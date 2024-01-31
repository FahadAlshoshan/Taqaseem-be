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
              "message": "Successfully authenticated!",
              "lang": "EN",
              "jwt":
              {
                "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjgzLCJleHAiOjE3MDY2ODc0ODN9.kDsnfC_TCKve8tLYRIsBahkeZib1n5lI5pU1bJu9wj8",
                "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNTk1OTA0MDU3IiwiaWF0IjoxNzA2Njg1NjAyLCJleHAiOjE3MTQ0NjE2MDJ9.I4a3TtjXrVTTg2aVHo5LNGvv_IxwSaacZfvdwA-N9k4"
              },
              "authenticated": true
          }
      """;
  public static final String API_EXAMPLE_401_OTP_AUTHENTICATE =
      """
          {
              "message": "Authentication unsuccessful!",
              "lang": "EN",
              "jwt":
              {
                "access_token": null,
                "refresh_token": null
              },
              "authenticated": false
          }
      """;

}
