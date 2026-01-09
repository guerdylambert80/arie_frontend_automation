#!/bin/bash

# ============================================
# Setup Script for Selenium Tests in CI/CD
# ============================================
# This script installs system dependencies needed for
# running browsers in headless mode on Linux

set -e  # Exit on any error

echo "=========================================="
echo "Setting up Selenium test environment"
echo "=========================================="

# Update package list
echo "Updating package list..."
sudo apt-get update -qq  # -qq for quiet mode

# Install system dependencies for Chrome and Firefox
echo "Installing system dependencies..."
sudo apt-get install -y \
    wget \
    unzip \
    curl \
    libxss1 \
    libindicator7 \
    fonts-liberation \
    libnspr4 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxi6 \
    libxtst6 \
    xdg-utils \
    xvfb  # Virtual display for headless mode

# Note: libappindicator1 and libasound2 are deprecated/removed in newer Ubuntu versions
# They are not required for headless browser execution, so they have been removed

echo "System dependencies installed successfully!"

# Note: WebDriverManager (used in your BaseTest) will automatically
# download and manage ChromeDriver and GeckoDriver, so manual
# installation is not needed.

echo "=========================================="
echo "Setup complete!"
echo "=========================================="
