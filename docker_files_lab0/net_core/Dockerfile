# FROM mcr.microsoft.com/dotnet/sdk:6.0

# WORKDIR /usr/src/app

# COPY ./source/ ./
# EXPOSE 4080

# CMD [ "/bin/bash", "start.sh"]


FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /source

# Copy everything
COPY source/MyWebApp/ .
# Restore as distinct layers
RUN dotnet restore
# Build and publish a release
RUN dotnet publish -c Release -o /app

# Build runtime image
FROM mcr.microsoft.com/dotnet/aspnet:6.0
WORKDIR /app
COPY --from=build /app .
COPY source/start.sh .

EXPOSE 4080
CMD [ "/bin/bash", "start.sh" ]