.PHONY: deploy reobf restart clean

JAR_NAME=UniverseCoreV2-1.0-reobf.jar
BUILD_JAR=./build/libs/$(JAR_NAME)
PLUGIN_JAR=./docker/paper/plugins/$(JAR_NAME)
GRADLE=./gradlew

deploy: reobf restart

reobf:
	rm -f $(BUILD_JAR)
	$(GRADLE) reobfJar --no-daemon
	cp $(BUILD_JAR) $(PLUGIN_JAR)

restart:
	cd docker && docker compose restart

clean:
	rm -f $(BUILD_JAR)