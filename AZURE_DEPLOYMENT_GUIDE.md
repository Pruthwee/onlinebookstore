# Azure Cloud Deployment Guide

## Cloud Readiness Fixes Applied

This application has been modernized for Azure cloud deployment with the following improvements:

### 1. Configuration Management
- **Fixed**: Hard-coded configuration values
- **Solution**: Externalized all configuration to environment variables
- **Azure Service**: Use Azure App Configuration or App Service Configuration

### 2. Secrets Management
- **Fixed**: Hard-coded credentials in source code
- **Solution**: Configuration reads from environment variables
- **Azure Service**: Use Azure Key Vault with Managed Identity

### 3. Database Connection Pooling
- **Fixed**: Direct JDBC connections without timeouts
- **Solution**: Implemented HikariCP connection pooling with proper timeouts
- **Benefits**: Better resource management, connection reuse, timeout handling

### 4. Session State Management
- **Fixed**: In-memory HTTP session storage
- **Solution**: Session-compatible architecture ready for Redis
- **Azure Service**: Use Azure Cache for Redis for distributed sessions

### 5. Packaging
- **Fixed**: WAR packaging requiring external application server
- **Solution**: Converted to JAR packaging (can still deploy as WAR if needed)
- **Benefits**: Smaller container images, faster deployment

## Environment Variables Required

Set these environment variables in Azure App Service Configuration:

```bash
# Database Configuration
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_HOST=jdbc:mysql://<your-azure-mysql-server>.mysql.database.azure.com
DB_PORT=3306
DB_NAME=onlinebookstore
DB_USERNAME=<your-db-username>
DB_PASSWORD=<your-db-password>

# Optional: Redis Configuration (for session management)
REDIS_HOST=<your-redis-cache>.redis.cache.windows.net
REDIS_PORT=6380
REDIS_PASSWORD=<your-redis-key>
REDIS_SSL=true

# Optional: Azure Key Vault (for secrets)
AZURE_KEYVAULT_URI=https://<your-keyvault>.vault.azure.net/
AZURE_TENANT_ID=<your-tenant-id>
```

## Azure Services Integration

### Azure Database for MySQL
1. Create Azure Database for MySQL Flexible Server
2. Configure firewall rules to allow Azure services
3. Set connection string in environment variables
4. Enable SSL/TLS for secure connections

### Azure Cache for Redis
1. Create Azure Cache for Redis (Standard or Premium tier)
2. Enable non-SSL port or configure SSL
3. Set Redis connection details in environment variables
4. Uncomment Redis dependencies in pom.xml

### Azure Key Vault
1. Create Azure Key Vault
2. Enable Managed Identity for your App Service
3. Grant Key Vault access to Managed Identity
4. Store secrets in Key Vault
5. Reference secrets via environment variables

### Azure App Service
1. Create App Service (Linux or Windows)
2. Choose Java 8 runtime
3. Configure environment variables in Configuration
4. Enable Application Insights for monitoring
5. Deploy JAR file or use CI/CD

## Deployment Options

### Option 1: Azure App Service (Recommended)
```bash
# Deploy JAR directly
az webapp deploy --resource-group <rg> --name <app-name> --src-path target/onlinebookstore.jar --type jar
```

### Option 2: Azure Container Apps
```bash
# Build container image
docker build -t onlinebookstore:latest .

# Push to Azure Container Registry
az acr build --registry <acr-name> --image onlinebookstore:latest .

# Deploy to Container Apps
az containerapp create --name onlinebookstore --resource-group <rg> --image <acr-name>.azurecr.io/onlinebookstore:latest
```

### Option 3: Azure Kubernetes Service (AKS)
```bash
# Deploy using Kubernetes manifests
kubectl apply -f k8s/deployment.yaml
```

## Migration Path to Full Cloud-Native

### Phase 1: Current State (Completed)
- ✅ Externalized configuration
- ✅ Connection pooling with timeouts
- ✅ Session-ready architecture
- ✅ JAR packaging

### Phase 2: Redis Session Management
1. Uncomment Redis dependencies in pom.xml
2. Configure RedisSessionManager with actual Redis client
3. Update session handling to use Redis
4. Test with Azure Cache for Redis

### Phase 3: Spring Boot Migration (Optional)
1. Migrate to Spring Boot framework
2. Use Spring Session with Redis
3. Use Spring Data JPA for database access
4. Use Spring Cloud Azure for Azure services integration

### Phase 4: Microservices (Optional)
1. Split into microservices (User Service, Book Service, Order Service)
2. Use Azure Service Bus for messaging
3. Use Azure API Management for API gateway
4. Implement distributed tracing with Application Insights

## Monitoring and Observability

### Azure Application Insights
- Enable Application Insights in App Service
- Monitor application performance
- Track exceptions and errors
- View dependency calls (database, Redis)

### Azure Monitor
- Set up alerts for high CPU, memory usage
- Monitor database connection pool metrics
- Track Redis cache hit/miss rates

### Logging
- Application uses SLF4J with Logback
- Logs are automatically captured by Azure App Service
- View logs in Azure Portal or using Azure CLI

## Security Best Practices

1. **Never commit secrets**: Use Azure Key Vault
2. **Use Managed Identity**: Avoid storing credentials
3. **Enable SSL/TLS**: For all connections (database, Redis)
4. **Network Security**: Use Virtual Networks and Private Endpoints
5. **Authentication**: Consider Azure AD B2C for user authentication
6. **API Security**: Use Azure API Management with OAuth 2.0

## Cost Optimization

1. **Right-size resources**: Start with Basic tier, scale as needed
2. **Use Reserved Instances**: For predictable workloads
3. **Auto-scaling**: Configure based on CPU/memory metrics
4. **Connection pooling**: Reduces database costs
5. **Redis caching**: Reduces database load

## Troubleshooting

### Connection Issues
- Check firewall rules in Azure Database
- Verify environment variables are set correctly
- Check connection pool settings in DBUtil.java

### Session Issues
- Verify Redis connection if using Redis sessions
- Check session timeout settings
- Monitor Redis cache metrics

### Performance Issues
- Review Application Insights performance data
- Check database query performance
- Monitor connection pool utilization
- Review Redis cache hit rates

## Support and Resources

- [Azure App Service Documentation](https://docs.microsoft.com/azure/app-service/)
- [Azure Database for MySQL](https://docs.microsoft.com/azure/mysql/)
- [Azure Cache for Redis](https://docs.microsoft.com/azure/azure-cache-for-redis/)
- [Azure Key Vault](https://docs.microsoft.com/azure/key-vault/)
- [Azure Application Insights](https://docs.microsoft.com/azure/azure-monitor/app/app-insights-overview)
