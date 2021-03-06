/**
 * Copyright 2015 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.apereo.openlrs.storage.mongo;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

/**
 * @author ggilbert
 *
 */
@ConditionalOnProperty(name="openlrs.reader", havingValue="MongoReader")
@Component
public interface MongoEventRepository extends MongoRepository<EventMongo, String> {
  EventMongo findByTenantIdAndEventId(String tenantId, String eventId);
  Page<EventMongo> findByTenantIdAndEventGroupId(String tenantId, String context, Pageable pageable);
  Page<EventMongo> findByTenantIdAndEventGroupIdAndEventActorId(String tenantId, String context, String user, Pageable pageable);
  
  
  //@Query("select event from EventMongo event where event.Tenantid = ?1 and event.group.id = %?2%")
  @Query(value="{ 'tenantId' : { $regex: ?0 } ,'event.group.id' : { $regex: ?1 }}")
  Page<EventMongo> findByTenantIdAndEventGroupIdIn(String tenantId, String context, Pageable pageable);
  
  
  Page<EventMongo> findByTenantIdAndEventActorId(String tenantId, String user, Pageable pageable);
  Page<EventMongo> findByTenantId(String tenantId, Pageable pageable);
}
