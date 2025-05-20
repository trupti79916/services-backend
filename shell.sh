#!/bin/bash

BASE_DIR="src/main/java/com/eServices"

# List of directories to create
dirs=(
  "$BASE_DIR/config"
  "$BASE_DIR/controller"
  "$BASE_DIR/dto/request"
  "$BASE_DIR/dto/response"
  "$BASE_DIR/service"
  "$BASE_DIR/security/jwt"
  "$BASE_DIR/security/services"
)

# List of files to create
files=(
  "$BASE_DIR/config/SecurityConfig.java"
  "$BASE_DIR/config/JwtConfig.java"

  "$BASE_DIR/controller/AuthController.java"
  "$BASE_DIR/controller/ServiceController.java"
  "$BASE_DIR/controller/OrderController.java"
  "$BASE_DIR/controller/FeedbackController.java"
  "$BASE_DIR/controller/UserController.java"

  "$BASE_DIR/dto/request/LoginRequest.java"
  "$BASE_DIR/dto/request/RegisterRequest.java"
  "$BASE_DIR/dto/request/ServiceRequest.java"
  "$BASE_DIR/dto/request/OrderRequest.java"
  "$BASE_DIR/dto/request/FeedbackRequest.java"

  "$BASE_DIR/dto/response/JwtResponse.java"
  "$BASE_DIR/dto/response/MessageResponse.java"
  "$BASE_DIR/dto/response/ServiceResponse.java"
  "$BASE_DIR/dto/response/OrderResponse.java"
  "$BASE_DIR/dto/response/FeedbackResponse.java"

  "$BASE_DIR/service/UserService.java"
  "$BASE_DIR/service/ServiceService.java"
  "$BASE_DIR/service/OrderService.java"
  "$BASE_DIR/service/FeedbackService.java"
  "$BASE_DIR/service/AuthService.java"

  "$BASE_DIR/security/jwt/JwtUtils.java"
  "$BASE_DIR/security/jwt/AuthTokenFilter.java"
  "$BASE_DIR/security/services/UserDetailsServiceImpl.java"
)

# Create directories
for dir in "${dirs[@]}"; do
  mkdir -p "$dir"
done

# Create files
for file in "${files[@]}"; do
  touch "$file"
done

echo "Directory structure and files created successfully."
