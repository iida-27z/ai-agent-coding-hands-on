FROM eclipse-temurin:25-jdk

RUN apt update && apt install -y --no-install-recommends curl git gh maven nodejs \
    && curl -fsSL https://antigravity.google/cli/install.sh | bash \
    && rm -rf /var/lib/apt/lists/*
