// README Generator - JavaScript

// ===================================
// State Management
// ===================================
const state = {
    technologies: [],
    currentMarkdown: '',
    currentTab: 'rendered'
};

// ===================================
// DOM Elements
// ===================================
const elements = {
    form: document.getElementById('readmeForm'),
    techInput: document.getElementById('technologiesInput'),
    techTags: document.getElementById('techTags'),
    techHidden: document.getElementById('technologies'),
    previewContent: document.getElementById('previewContent'),
    previewActions: document.getElementById('previewActions'),
    generateBtn: document.getElementById('generateBtn'),
    copyBtn: document.getElementById('copyBtn'),
    downloadBtn: document.getElementById('downloadBtn'),
    exportBtn: document.getElementById('exportBtn'),
    exportModal: document.getElementById('exportModal'),
    modalClose: document.querySelector('.modal-close'),
    cancelExport: document.getElementById('cancelExport'),
    confirmExport: document.getElementById('confirmExport'),
    notification: document.getElementById('notification'),
    loadingOverlay: document.getElementById('loadingOverlay'),
    tabBtns: document.querySelectorAll('.tab-btn')
};

// ===================================
// Technology Tags Management
// ===================================
elements.techInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        const tech = e.target.value.trim();
        
        if (tech && !state.technologies.includes(tech)) {
            state.technologies.push(tech);
            renderTechTags();
            e.target.value = '';
        }
    }
});

function renderTechTags() {
    elements.techTags.innerHTML = state.technologies.map((tech, index) => `
        <span class="tech-tag">
            ${tech}
            <span class="tech-tag-remove" onclick="removeTech(${index})">×</span>
        </span>
    `).join('');
    
    // Update hidden input
    elements.techHidden.value = JSON.stringify(state.technologies);
}

function removeTech(index) {
    state.technologies.splice(index, 1);
    renderTechTags();
}

// ===================================
// Form Submission & README Generation
// ===================================
elements.form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    // Collect form data
    const formData = new FormData(e.target);
    const data = {
        projectName: formData.get('projectName'),
        tagline: formData.get('tagline'),
        description: formData.get('description'),
        templateType: formData.get('templateType'),
        technologies: state.technologies,
        features: formData.get('features'),
        installation: formData.get('installation'),
        usage: formData.get('usage'),
        repositoryUrl: formData.get('repositoryUrl'),
        demoUrl: formData.get('demoUrl'),
        license: formData.get('license'),
        author: formData.get('author'),
        includeBadges: formData.get('includeBadges') === 'on',
        includeTableOfContents: formData.get('includeTableOfContents') === 'on',
        includeScreenshots: formData.get('includeScreenshots') === 'on',
        includeContributing: formData.get('includeContributing') === 'on',
        includeLicense: formData.get('includeLicense') === 'on'
    };
    
    await generateReadme(data);
});

async function generateReadme(data) {
    showLoading(true);
    
    try {
        const response = await fetch('/api/generate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            state.currentMarkdown = result.markdown;
            updatePreview();
            showNotification('README généré avec succès !', 'success');
            elements.previewActions.style.display = 'flex';
        } else {
            showNotification('Erreur: ' + (result.error || 'Génération échouée'), 'error');
        }
    } catch (error) {
        console.error('Error generating README:', error);
        showNotification('Erreur de connexion au serveur', 'error');
    } finally {
        showLoading(false);
    }
}

// ===================================
// Preview Tab Management
// ===================================
elements.tabBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const tab = btn.dataset.tab;
        
        // Update active tab
        elements.tabBtns.forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        
        state.currentTab = tab;
        updatePreview();
    });
});

function updatePreview() {
    if (!state.currentMarkdown) {
        return;
    }
    
    if (state.currentTab === 'rendered') {
        // Render Markdown as HTML
        elements.previewContent.innerHTML = `
            <div class="markdown-preview">
                ${marked.parse(state.currentMarkdown)}
            </div>
        `;
    } else {
        // Show raw Markdown
        elements.previewContent.innerHTML = `
            <pre><code>${escapeHtml(state.currentMarkdown)}</code></pre>
        `;
    }
}

// ===================================
// Action Buttons
// ===================================
elements.copyBtn.addEventListener('click', () => {
    navigator.clipboard.writeText(state.currentMarkdown)
        .then(() => {
            showNotification('README copié dans le presse-papiers !', 'success');
        })
        .catch(err => {
            console.error('Copy failed:', err);
            showNotification('Erreur lors de la copie', 'error');
        });
});

elements.downloadBtn.addEventListener('click', () => {
    const blob = new Blob([state.currentMarkdown], { type: 'text/markdown' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'README.md';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    
    showNotification('README téléchargé !', 'success');
});

elements.exportBtn.addEventListener('click', () => {
    // Pre-fill repository URL if available
    const repoUrl = document.getElementById('repositoryUrl').value;
    if (repoUrl) {
        document.getElementById('exportRepoUrl').value = repoUrl;
    }
    
    openModal();
});

// ===================================
// Export Modal
// ===================================
function openModal() {
    elements.exportModal.classList.add('active');
}

function closeModal() {
    elements.exportModal.classList.remove('active');
}

elements.modalClose.addEventListener('click', closeModal);
elements.cancelExport.addEventListener('click', closeModal);

elements.exportModal.addEventListener('click', (e) => {
    if (e.target === elements.exportModal) {
        closeModal();
    }
});

elements.confirmExport.addEventListener('click', async () => {
    const repoUrl = document.getElementById('exportRepoUrl').value;
    const token = document.getElementById('githubToken').value;
    
    if (!repoUrl || !token) {
        showNotification('Veuillez remplir tous les champs', 'warning');
        return;
    }
    
    await exportToGitHub(repoUrl, token);
});

async function exportToGitHub(repositoryUrl, token) {
    showLoading(true);
    closeModal();
    
    try {
        const response = await fetch('/api/export', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                repositoryUrl: repositoryUrl,
                readmeContent: state.currentMarkdown,
                githubToken: token
            })
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showNotification('README exporté vers GitHub avec succès !', 'success');
            
            // Clear token for security
            document.getElementById('githubToken').value = '';
        } else {
            showNotification('Erreur: ' + (result.error || 'Export échoué'), 'error');
        }
    } catch (error) {
        console.error('Error exporting to GitHub:', error);
        showNotification('Erreur de connexion lors de l\'export', 'error');
    } finally {
        showLoading(false);
    }
}

// ===================================
// Utility Functions
// ===================================
function showLoading(show) {
    elements.loadingOverlay.style.display = show ? 'flex' : 'none';
}

function showNotification(message, type = 'success') {
    elements.notification.textContent = message;
    elements.notification.className = `notification ${type} show`;
    
    setTimeout(() => {
        elements.notification.classList.remove('show');
    }, 4000);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ===================================
// Initialize
// ===================================
document.addEventListener('DOMContentLoaded', () => {
    console.log('README Generator Pro initialized');
    
    // Load available templates from API
    loadTemplates();
});

async function loadTemplates() {
    try {
        const response = await fetch('/api/templates');
        const data = await response.json();
        
        // Populate license select if needed
        if (data.licenses && data.licenses.length > 0) {
            const licenseSelect = document.getElementById('license');
            const currentOptions = Array.from(licenseSelect.options).map(opt => opt.value);
            
            data.licenses.forEach(license => {
                if (!currentOptions.includes(license)) {
                    const option = document.createElement('option');
                    option.value = license;
                    option.textContent = license;
                    licenseSelect.appendChild(option);
                }
            });
        }
        
        console.log('Templates loaded:', data);
    } catch (error) {
        console.error('Error loading templates:', error);
    }
}

// ===================================
// Keyboard Shortcuts
// ===================================
document.addEventListener('keydown', (e) => {
    // Ctrl/Cmd + Enter to generate
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
        e.preventDefault();
        elements.form.dispatchEvent(new Event('submit'));
    }
    
    // Escape to close modal
    if (e.key === 'Escape') {
        closeModal();
    }
});

// ===================================
// Auto-save to localStorage (optional)
// ===================================
function saveToLocalStorage() {
    const formData = {
        projectName: document.getElementById('projectName').value,
        description: document.getElementById('description').value,
        technologies: state.technologies
    };
    
    localStorage.setItem('readme-generator-draft', JSON.stringify(formData));
}

function loadFromLocalStorage() {
    const saved = localStorage.getItem('readme-generator-draft');
    if (saved) {
        try {
            const data = JSON.parse(saved);
            if (data.projectName) document.getElementById('projectName').value = data.projectName;
            if (data.description) document.getElementById('description').value = data.description;
            if (data.technologies) {
                state.technologies = data.technologies;
                renderTechTags();
            }
        } catch (e) {
            console.error('Error loading saved data:', e);
        }
    }
}

// Auto-save on input changes (debounced)
let saveTimeout;
document.querySelectorAll('input, textarea, select').forEach(input => {
    input.addEventListener('input', () => {
        clearTimeout(saveTimeout);
        saveTimeout = setTimeout(saveToLocalStorage, 1000);
    });
});

// Load saved data on page load
// loadFromLocalStorage(); // Uncomment to enable auto-save