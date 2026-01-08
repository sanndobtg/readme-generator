#!/bin/bash

# ===================================
# README Generator Pro - Deploy Script
# Automated Docker Build and Deploy
# ===================================

set -e

# Colors for output 
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "╔══════════════════════════════════════════╗"
echo "║  README Generator - Deploy Script        ║"
echo "╚══════════════════════════════════════════╝"
echo -e "${NC}"

# Variables
IMAGE_NAME="readme-generator"
VERSION=${1:-latest}
DOCKER_USERNAME=${DOCKER_USERNAME:-""}

# Functions
function print_success() {
    echo -e "${GREEN} $1${NC}"
}

function print_error() {
    echo -e "${RED} $1${NC}"
}

function print_info() {
    echo -e "${YELLOW}  $1${NC}"
}

function check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed"
        exit 1
    fi
    print_success "Docker found: $(docker --version)"
}

function check_docker_compose() {
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed"
        exit 1
    fi
    print_success "Docker Compose found: $(docker-compose --version)"
}

function clean_build() {
    print_info "Cleaning previous builds..."
    mvn clean
    print_success "Clean completed"
}

function run_tests() {
    print_info "Running tests..."
    if mvn test; then
        print_success "All tests passed"
    else
        print_error "Tests failed"
        exit 1
    fi
}

function build_docker_image() {
    print_info "Building Docker image..."
    
    if docker build -t ${IMAGE_NAME}:${VERSION} -t ${IMAGE_NAME}:latest .; then
        print_success "Docker image built successfully"
    else
        print_error "Docker build failed"
        exit 1
    fi
}

function test_image() {
    print_info "Testing Docker image..."
    
    # Start container
    docker run -d --name ${IMAGE_NAME}-test -p 8081:8080 ${IMAGE_NAME}:latest
    
    # Wait for startup
    sleep 10
    
    # Test health endpoint
    if curl -f http://localhost:8081/actuator/health; then
        print_success "Container health check passed"
        docker stop ${IMAGE_NAME}-test
        docker rm ${IMAGE_NAME}-test
    else
        print_error "Container health check failed"
        docker logs ${IMAGE_NAME}-test
        docker stop ${IMAGE_NAME}-test
        docker rm ${IMAGE_NAME}-test
        exit 1
    fi
}

function push_to_registry() {
    if [ -z "$DOCKER_USERNAME" ]; then
        print_info "DOCKER_USERNAME not set, skipping push to registry"
        return
    fi
    
    print_info "Pushing to Docker Hub..."
    
    # Tag image
    docker tag ${IMAGE_NAME}:latest ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION}
    docker tag ${IMAGE_NAME}:latest ${DOCKER_USERNAME}/${IMAGE_NAME}:latest
    
    # Push
    if docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:${VERSION} && \
       docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:latest; then
        print_success "Image pushed to Docker Hub"
    else
        print_error "Push to Docker Hub failed"
        exit 1
    fi
}

function deploy_local() {
    print_info "Deploying locally with docker-compose..."
    
    # Stop existing containers
    docker-compose down 2>/dev/null || true
    
    # Start new containers
    if docker-compose up -d; then
        print_success "Application deployed locally"
        print_info "Access at: http://localhost:8080"
    else
        print_error "Deployment failed"
        exit 1
    fi
}

function show_logs() {
    print_info "Showing logs (Ctrl+C to exit)..."
    docker-compose logs -f
}

# Main execution
main() {
    echo ""
    print_info "Starting deployment process..."
    echo ""
    
    # Step 1: Check prerequisites
    check_docker
    check_docker_compose
    echo ""
    
    # Step 2: Clean and test
    clean_build
    
    # Skip tests if --skip-tests flag is provided
    if [[ ! " $@ " =~ " --skip-tests " ]]; then
        run_tests
    fi
    echo ""
    
    # Step 3: Build Docker image
    build_docker_image
    echo ""
    
    # Step 4: Test Docker image
    test_image
    echo ""
    
    # Step 5: Push to registry (optional)
    if [[ " $@ " =~ " --push " ]]; then
        push_to_registry
        echo ""
    fi
    
    # Step 6: Deploy locally
    if [[ ! " $@ " =~ " --no-deploy " ]]; then
        deploy_local
        echo ""
        
        # Show logs if --logs flag is provided
        if [[ " $@ " =~ " --logs " ]]; then
            show_logs
        fi
    fi
    
    echo ""
    print_success "Deployment completed successfully!"
    echo ""
    print_info "Next steps:"
    echo "  - Access application: http://localhost:8080"
    echo "  - View logs: docker-compose logs -f"
    echo "  - Stop: docker-compose down"
    echo ""
}

# Help message
if [[ " $@ " =~ " --help " ]] || [[ " $@ " =~ " -h " ]]; then
    echo "Usage: ./deploy.sh [VERSION] [OPTIONS]"
    echo ""
    echo "OPTIONS:"
    echo "  --skip-tests    Skip running tests"
    echo "  --push          Push image to Docker Hub"
    echo "  --no-deploy     Build only, don't deploy"
    echo "  --logs          Show logs after deployment"
    echo "  --help, -h      Show this help message"
    echo ""
    echo "EXAMPLES:"
    echo "  ./deploy.sh                    # Deploy latest version locally"
    echo "  ./deploy.sh v1.0.0 --push     # Build v1.0.0 and push to Docker Hub"
    echo "  ./deploy.sh --skip-tests       # Deploy without running tests"
    echo "  ./deploy.sh --logs             # Deploy and show logs"
    echo ""
    exit 0
fi

# Run main function
main "$@"
