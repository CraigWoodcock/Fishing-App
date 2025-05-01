/**
 * Service package containing business logic implementations for the Fishing Application.
 *
 * <p>This package contains service classes that implement business logic and transaction management.
 * Services act as an intermediary layer between controllers and repositories, providing:
 * <ul>
 *   <li>Data validation and business rules enforcement</li>
 *   <li>Transaction management</li>
 *   <li>Complex operations involving multiple repositories</li>
 *   <li>Security and access control logic</li>
 * </ul>
 *
 * <p>Key services:
 * <ul>
 *   <li>UserService - User account management and authentication</li>
 *   <li>SessionService - Fishing session operations</li>
 *   <li>AnglerService - Angler profile management</li>
 *   <li>CustomUserDetailsService - Security user details implementation</li>
 * </ul>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.service;