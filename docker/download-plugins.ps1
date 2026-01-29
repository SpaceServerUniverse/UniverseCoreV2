# Script to download external plugins listed in plugins.txt
# Usage: .\download-plugins.ps1

$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$PluginsDir = Join-Path $ScriptDir "paper/plugins"
$PluginsList = Join-Path $ScriptDir "plugins.txt"

Write-Host "==========================================" -ForegroundColor White
Write-Host "External Plugin Downloader" -ForegroundColor White
Write-Host "==========================================" -ForegroundColor White
Write-Host ""

# Check if plugins.txt exists
if (-not (Test-Path $PluginsList)) {
    Write-Host "Error: plugins.txt not found at $PluginsList" -ForegroundColor Red
    exit 1
}

# Create plugins directory if it doesn't exist
if (-not (Test-Path $PluginsDir)) {
    New-Item -ItemType Directory -Path $PluginsDir | Out-Null
}

# Read plugins.txt and download each plugin
$downloadCount = 0
$skipCount = 0

Get-Content $PluginsList | ForEach-Object {
    $line = $_.Trim()

    # Skip empty lines and comments
    if ([string]::IsNullOrWhiteSpace($line) -or $line.StartsWith("#")) {
        return
    }

    # Extract filename from URL
    $filename = Split-Path $line -Leaf
    $outputPath = Join-Path $PluginsDir $filename

    # Check if file already exists
    if (Test-Path $outputPath) {
        Write-Host "⊘ Skipping: $filename (already exists)" -ForegroundColor Yellow
        $script:skipCount++
        return
    }

    # Download the plugin
    Write-Host "↓ Downloading: $filename" -ForegroundColor Green
    try {
        Invoke-WebRequest -Uri $line -OutFile $outputPath -UseBasicParsing
        Write-Host "✓ Downloaded: $filename" -ForegroundColor Green
        $script:downloadCount++
    }
    catch {
        Write-Host "✗ Failed to download: $line" -ForegroundColor Red
        Write-Host "  Error: $_" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "==========================================" -ForegroundColor White
Write-Host "Downloaded: $downloadCount plugin(s)" -ForegroundColor Green
Write-Host "Skipped: $skipCount plugin(s)" -ForegroundColor Yellow
Write-Host "==========================================" -ForegroundColor White
Write-Host ""
Write-Host "Plugins are saved in: $PluginsDir"
Write-Host "To use these plugins, start the server with: docker compose up"
