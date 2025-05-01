// File: com/craigwoodcock/fishingapp/controller/webController/package-info.java
/**
 * Web controllers package handling all web interface endpoints for the Fishing Application.
 *
 * <p>This package contains Spring MVC controllers that:
 * <ul>
 *   <li>Handle web page requests</li>
 *   <li>Process form submissions</li>
 *   <li>Manage web sessions</li>
 *   <li>Return view templates</li>
 * </ul>
 *
 * <p>Key controllers:
 * <ul>
 *   <li>AuthController - Login and registration pages</li>
 *   <li>SessionController - Fishing session management views</li>
 *   <li>DashboardController - User dashboard and statistics</li>
 * </ul>
 *
 * <p>View templates used by these controllers are located in src/main/resources/templates
 * and include:
 * <ul>
 *   <li>login.html - User authentication</li>
 *   <li>register.html - New user registration</li>
 *   <li>dashboard.html - User dashboard</li>
 *   <li>new-session.html - Create fishing session</li>
 *   <li>view-session.html - View session details</li>
 * </ul>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.controller.webController;