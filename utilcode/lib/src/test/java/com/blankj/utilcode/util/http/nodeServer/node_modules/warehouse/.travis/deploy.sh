#!/bin/bash
# Decrypt the private key
openssl aes-256-cbc -K $encrypted_a808e857cd51_key -iv $encrypted_a808e857cd51_iv -in .travis/ssh_key.enc -out ~/.ssh/id_rsa -d
# Set the permission of the key
chmod 600 ~/.ssh/id_rsa
# Start SSH agent
eval $(ssh-agent)
# Add the private key to the system
ssh-add ~/.ssh/id_rsa
# Copy SSH config
cp .travis/ssh_config ~/.ssh/config
# Set Git config
git config --global user.name "Tommy Chen"
git config --global user.email tommy351@gmail.com
# Clone the repository
git clone git@github.com:tommy351/warehouse.git --branch gh-pages docs
# Generate JSDoc
npm run jsdoc
# Push to GitHub
cd docs
git add -A
git commit -m "Update docs"
git push origin gh-pages --force
