/**
 * ID package containing composite key classes for JPA entities in the Fishing Application.
 *
 * <p>This package contains classes that implement composite primary keys for
 * entities that require multiple fields for unique identification. These classes:
 * <ul>
 *   <li>Implement Serializable for JPA requirements</li>
 *   <li>Override equals() and hashCode() for proper entity comparison</li>
 *   <li>Provide necessary constructors for JPA</li>
 * </ul>
 *
 * <p>Key classes:
 * <ul>
 *   <li>AnglerSessionId - Composite key for AnglerSession entity combining angler and session IDs</li>
 * </ul>
 *
 * <p>These classes are used primarily in conjunction with @IdClass annotations
 * on their corresponding entity classes.</p>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.model.id;