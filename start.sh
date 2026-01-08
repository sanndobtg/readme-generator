#!/bin/bash

# ===================================================
# README Generator - Launch Script for macOS
# Author: README Generator Team
# Description: Quick start script for the application
# ====================================================

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "╔══════════════════════════════════════════╗"
echo "║  README Generator - Quick Start          ║"
echo "╚══════════════════════════════════════════╝"
echo -e "${NC}"

# Check Java
echo -e "${YELLOW}Checking Java installation...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}Java is not installed${NC}"
    echo "Please install Java 17 or higher:"
    echo "  brew install openjdk@17"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}Java 17 or higher is required${NC}"
    echo "Current version: $JAVA_VERSION"
    exit 1
fi
echo -e "${GREEN} Java $JAVA_VERSION found${NC}"

# Check Maven
echo -e "${YELLOW} Checking Maven installation...${NC}"
if ! command -v mvn &> /dev/null; then
    echo -e "${RED} Maven is not installed${NC}"
    echo "Please install Maven 3.6 or higher:"
    echo "  brew install maven"
    exit 1
fi
echo -e "${GREEN} Maven $(mvn -version | head -n 1 | cut -d' ' -f3) found${NC}"

# Build the project
echo ""
echo -e "${YELLOW} Building the project...${NC}"
if mvn clean package -DskipTests -q; then
    echo -e "${GREEN} Build successful!${NC}"
else
    echo -e "${RED} Build failed${NC}"
    echo "Please check the error messages above"
    exit 1
fi

# Start the application
echo ""
echo -e "${GREEN} Starting README Generator...${NC}"
echo -e "${BLUE}   Application will be available at: http://localhost:8080${NC}"
echo ""
echo -e "${YELLOW}Press Ctrl+C to stop the application${NC}"
echo ""
echo "═══════════════════════════════════════════════════════════"
echo ""

# Run Spring Boot
mvn spring-boot:run
