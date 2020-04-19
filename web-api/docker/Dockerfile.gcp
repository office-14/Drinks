FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /app

COPY *.sln .
COPY src/Project.API/Project.API.csproj src/Project.API/Project.API.csproj
COPY src/Project.IntegrationTests/Project.IntegrationTests.csproj src/Project.IntegrationTests/Project.IntegrationTests.csproj
RUN dotnet restore

COPY src/. ./src/
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/core/aspnet:3.1 AS runtime
COPY docker/gcp-entrypoint.sh /gcp-entrypoint.sh
WORKDIR /app
COPY --from=build /app/out ./
ENTRYPOINT ["/gcp-entrypoint.sh", "dotnet", "Project.API.dll"]