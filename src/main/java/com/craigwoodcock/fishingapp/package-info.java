/**
 * Main package for the FishingApp application.
 *
 * <p>
 * The FishingApp provides a set of APIs for user authentication and other functionalities.
 * Below are the steps to get started with the authentication API:
 * </p>
 *
 * <h2>Getting Started</h2>
 *
 * <p><strong>1. Register a New User:</strong></p>
 * <p>To register a new user, send a POST request to <code>/api/auth/register</code> with the following JSON body:</p>
 * <pre>
 * {
 *   "name": "Your Name",
 *   "username": "yourUsername",
 *   "email": "your.email@example.com",
 *   "password": "yourSecurePassword"
 * }
 * </pre>
 * <p>Ensure the <code>Content-Type</code> header is set to <code>application/json</code>.</p>
 *
 * <p><strong>2. Login:</strong></p>
 * <p>Once registered, log in by sending a POST request to <code>/api/auth/login</code> with the following JSON body:</p>
 * <pre>
 * {
 *   "username": "yourUsername",
 *   "password": "yourSecurePassword"
 * }
 * </pre>
 * <p>Ensure the <code>Content-Type</code> header is set to <code>application/json</code>.</p>
 *
 * <p>The response will contain a token in the following format:</p>
 * <pre>
 * {
 *   "token": "yourJWTtokenHere"
 * }
 * </pre>
 *
 * <p><strong>3. Access Secured Endpoints:</strong></p>
 * <p>Use the token from the login response to access secured endpoints. Add an <code>Authorization</code> header
 * with the value <code>Bearer {token}</code> to your requests:</p>
 * <pre>
 * Authorization: Bearer yourJWTtokenHere
 * </pre>
 *
 * <h2>Notes:</h2>
 * <ul>
 *   <li>Ensure your API client can handle JSON payloads and headers appropriately.</li>
 *   <li>Tokens may expire; refresh or re-login as needed.</li>
 * </ul>
 *
 * <p>For more detailed documentation on the available endpoints and their functionality, refer to the API documentation or
 * explore the controllers in the <code>com.craigwoodcock.fishingapp.controller</code> package.</p>
 */
package com.craigwoodcock.fishingapp;
