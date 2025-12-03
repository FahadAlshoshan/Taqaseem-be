package com.app.taqaseem.constant;

public class SwaggerApiExamples {
  public static final String API_EXAMPLE_200_REFRESH_JWT =
      """
          {
              "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjgzLCJleHAiOjE3MDY2ODc0ODN9.kDsnfC_TCKve8tLYRIsBahkeZib1n5lI5pU1bJu9wj8",
              "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjAyLCJleHAiOjE3MTQ0NjE2MDJ9.I4a3TtjXrVTTg2aVHo5LNGvv_IxwSaacZfvdwA-N9k4"
          }
      """;
  public static final String API_EXAMPLE_401_REFRESH_JWT =
      """
          {
              "message": "Invalid refresh token, phone number is null."
          }
      """;
  public static final String API_EXAMPLE_500_OTP_GENERATE =
      """
          {
              "message": "Cache error occurred"
          }
      """;
  public static final String API_EXAMPLE_200_OTP_AUTHENTICATE =
      """
          {
              "messageEN": "Successfully authenticated",
              "messageAR": "تم توثيق رمز التحقق بنجاح",
              "jwt":
              {
                "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjgzLCJleHAiOjE3MDY2ODc0ODN9.kDsnfC_TCKve8tLYRIsBahkeZib1n5lI5pU1bJu9wj8",
                "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjAyLCJleHAiOjE3MTQ0NjE2MDJ9.I4a3TtjXrVTTg2aVHo5LNGvv_IxwSaacZfvdwA-N9k4"
              },
              "authenticated": true
          }
      """;
  public static final String API_EXAMPLE_203_OTP_AUTHENTICATE =
      """
          {
              "messageEN": "Authentication failed",
              "messageAR": "فشل توثيق رمز التحقق",
              "jwt":
              {
                "access_token": null,
                "refresh_token": null
              },
              "authenticated": false
          }
      """;
  
  public static final String API_EXAMPLE_200_CHECK_USERNAME_AVAILABLE =
      """
          {
              "username": "Nawaf",
              "available": true,
              "message": "Username is available"
          }
      """;
  
  public static final String API_EXAMPLE_200_CHECK_USERNAME_TAKEN =
      """
          {
              "username": "existing_user",
              "available": false,
              "message": "Username is already taken"
          }
      """;
  
  public static final String API_EXAMPLE_400_CHECK_USERNAME_INVALID =
      """
          {
              "timestamp": "2025-12-03T15:52:00",
              "status": 400,
              "message": "Username must be between 3 and 20 characters"
          }
      """;

}
