#!/bin/bash

# Ensure docs directory exists
mkdir -p docs

# Clean previous Javadoc
rm -rf docs/apidocs

# Generate new Javadoc
mvn clean javadoc:javadoc

# Check if index.html exists, if not create it
if [ ! -f docs/index.html ]; then
    echo '<!DOCTYPE html>
<html>
<head>
    <title>Fishing App Documentation</title>
    <meta http-equiv="refresh" content="0;url=apidocs/index.html">
</head>
<body>
    <p>Redirecting to API documentation...</p>
</body>
</html>' > docs/index.html
fi

## Make script executable
#chmod +x generate-docs.sh