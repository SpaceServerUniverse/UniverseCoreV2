#!/bin/bash

# Script to download external plugins listed in plugins.txt
# Usage: ./download-plugins.sh

set -e

# Check if running on Windows
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$(uname -s)" == MINGW* || "$(uname -s)" == MSYS* ]]; then
    echo "Error: This script cannot be run on Windows."
    echo "Please use WSL (Windows Subsystem for Linux) or a Linux/macOS environment."
    exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PLUGINS_DIR="$SCRIPT_DIR/paper/plugins"
PLUGINS_LIST="$SCRIPT_DIR/plugins.txt"

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "=========================================="
echo "External Plugin Downloader"
echo "=========================================="
echo

# Check if plugins.txt exists
if [ ! -f "$PLUGINS_LIST" ]; then
    echo -e "${RED}Error: plugins.txt not found at $PLUGINS_LIST${NC}"
    exit 1
fi

# Create plugins directory if it doesn't exist
mkdir -p "$PLUGINS_DIR"

# Read plugins.txt and download each plugin
download_count=0
skip_count=0

while IFS= read -r line || [ -n "$line" ]; do
    # Skip empty lines and comments
    if [[ -z "$line" || "$line" =~ ^[[:space:]]*# ]]; then
        continue
    fi

    # Extract filename from URL
    filename=$(basename "$line")

    # Check if file already exists
    if [ -f "$PLUGINS_DIR/$filename" ]; then
        echo -e "${YELLOW}⊘ Skipping: $filename (already exists)${NC}"
        ((skip_count++))
        continue
    fi

    # Download the plugin
    echo -e "${GREEN}↓ Downloading: $filename${NC}"
    if curl -L -o "$PLUGINS_DIR/$filename" "$line" --fail --silent --show-error; then
        echo -e "${GREEN}✓ Downloaded: $filename${NC}"
        ((download_count++))
    else
        echo -e "${RED}✗ Failed to download: $line${NC}"
    fi
    echo
done < "$PLUGINS_LIST"

echo "=========================================="
echo -e "${GREEN}Downloaded: $download_count plugin(s)${NC}"
echo -e "${YELLOW}Skipped: $skip_count plugin(s)${NC}"
echo "=========================================="
echo
echo "Plugins are saved in: $PLUGINS_DIR"
echo "To use these plugins, start the server with: docker compose up"
