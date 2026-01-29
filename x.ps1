# Development utility script for UniverseCoreV2 (PowerShell version)

$ErrorActionPreference = "Stop"

$DockerCompose = "docker/compose.yaml"

$command = if ($args.Count -gt 0) { $args[0] } else { "help" }

switch ($command) {
    "start" {
        Write-Host "Building plugin with Gradle..." -ForegroundColor Cyan
        ./gradlew.bat clean reobfJar

        Write-Host "Copying built plugin to plugins directory..." -ForegroundColor Cyan
        New-Item -ItemType Directory -Path "docker/paper/plugins" -Force | Out-Null
        Copy-Item "build/libs/UniverseCoreV2-1.0-reobf.jar" "docker/paper/plugins/" -Force

        Write-Host "Downloading external plugins..." -ForegroundColor Cyan
        & ".\docker\download-plugins.ps1"

        Write-Host "Starting Docker Compose services..." -ForegroundColor Cyan
        docker compose -f $DockerCompose up

        Write-Host "Services started successfully!" -ForegroundColor Green
        Write-Host "Run './x.ps1 logs' to view server logs"
    }

    "restart" {
        Write-Host "Restarting Paper server..." -ForegroundColor Cyan
        docker compose -f $DockerCompose restart paper
        Write-Host "Server restarted successfully!" -ForegroundColor Green
    }

    "stop" {
        Write-Host "Stopping Docker Compose services..." -ForegroundColor Cyan
        docker compose -f $DockerCompose down
        Write-Host "Services stopped successfully!" -ForegroundColor Green
    }

    "clean" {
        Write-Host "Stopping services and removing volumes..." -ForegroundColor Cyan
        docker compose -f $DockerCompose down -v
        Write-Host "Cleanup completed successfully!" -ForegroundColor Green
    }

    "rcon" {
        $rconArgs = $args[1..($args.Count - 1)]
        docker compose -f $DockerCompose exec paper rcon-cli @rconArgs
    }

    "logs" {
        docker compose -f $DockerCompose logs -f paper
    }

    "help" {
        Write-Host @"
Usage: .\x.ps1 <command>

Commands:
  start      Build plugin, download external plugins, and start all services
  restart    Restart the Paper server container
  stop       Stop all Docker Compose services
  clean      Stop services and remove volumes
  rcon       Execute RCON command (usage: .\x.ps1 rcon <command>)
  logs       Stream Paper server logs
  help       Show this help message

Examples:
  .\x.ps1 start
  .\x.ps1 rcon "say Hello, World!"
  .\x.ps1 logs
"@
    }

    default {
        Write-Host "Error: Unknown command '$command'" -ForegroundColor Red
        Write-Host ""
        & $MyInvocation.MyCommand.Path "help"
        exit 1
    }
}
