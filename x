#!/bin/bash
# Development utility script for UniverseCoreV2

if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$(uname -s)" == MINGW* || "$(uname -s)" == MSYS* ]]; then
    echo "Error: This script cannot be run on Windows."
    echo "Please use WSL (Windows Subsystem for Linux) or a Linux/macOS environment."
    exit 1
fi

set -e

DOCKER_COMPOSE="docker/compose.yaml"

case "${1:-}" in
  start)
    echo "Building plugin with Gradle..."
    ./gradlew clean reobfJar

    echo "Copying built plugin to plugins directory..."
    mkdir -p docker/paper/plugins
    cp build/libs/UniverseCoreV2-1.0-reobf.jar docker/paper/plugins/

    echo "Downloading external plugins..."
    bash docker/download-plugins.sh

    echo "Starting Docker Compose services..."
    docker compose -f "$DOCKER_COMPOSE" up
    ;;

  restart)
    echo "Restarting Paper server..."
    docker compose -f "$DOCKER_COMPOSE" restart paper
    echo "Server restarted successfully!"
    ;;

  stop)
    echo "Stopping Docker Compose services..."
    docker compose -f "$DOCKER_COMPOSE" down
    echo "Services stopped successfully!"
    ;;

  clean)
    echo "Stopping services and removing volumes..."
    docker compose -f "$DOCKER_COMPOSE" down -v
    echo "Cleanup completed successfully!"
    ;;

  rcon)
    shift
    docker compose -f "$DOCKER_COMPOSE" exec paper rcon-cli "$@"
    ;;

  logs)
    docker compose -f "$DOCKER_COMPOSE" logs -f paper
    ;;

  help|*)
    if [ "${1:-}" != "help" ] && [ -n "${1:-}" ]; then
      echo "Error: Unknown command '${1}'"
      echo ""
    fi

    cat << EOF
Usage: ./x <command>

Commands:
  start      Build plugin, download external plugins, and start all services
  restart    Restart the Paper server container
  stop       Stop all Docker Compose services
  clean      Stop services and remove volumes
  rcon       Execute RCON command (usage: ./x rcon <command>)
  logs       Stream Paper server logs
  help       Show this help message

Examples:
  ./x start
  ./x rcon "say Hello, World!"
  ./x logs
EOF

    if [ "${1:-}" != "help" ] && [ -n "${1:-}" ]; then
      exit 1
    fi
    ;;
esac
