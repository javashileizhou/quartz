/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.quartz;

import java.util.Date;

import org.quartz.spi.MutableTrigger;
import org.quartz.utils.Key;

/**
 * <code>TriggerBuilder</code> is used to instantiate {@link Trigger}s.
 * 
 * <p>The builder will always try to keep itself in a valid state, with 
 * reasonable defaults set for calling build() at any point.  For instance
 * if you do not invoke <i>withSchedule(..)</i> method, a default schedule
 * of firing once immediately will be used.  As another example, if you
 * do not invoked <i>withIdentity(..)</i> a trigger name will be generated
 * for you.</p>
 *  
 * <p>Quartz provides a builder-style API for constructing scheduling-related
 * entities via a Domain-Specific Language (DSL).  The DSL can best be
 * utilized through the usage of static imports of the methods on the classes
 * <code>TriggerBuilder</code>, <code>JobBuilder</code>, 
 * <code>DateBuilder</code>, <code>JobKey</code>, <code>TriggerKey</code> 
 * and the various <code>ScheduleBuilder</code> implementations.</p>
 * 
 * <p>Client code can then use the DSL to write code such as this:</p>
 * <pre>
 *         JobDetail job = newJob(MyJob.class)
 *             .withIdentity("myJob")
 *             .build();
 *             
 *         Trigger trigger = newTrigger() 
 *             .withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
 *             .withSchedule(simpleSchedule()
 *                 .withIntervalInHours(1)
 *                 .repeatForever())
 *             .startAt(futureDate(10, MINUTES))
 *             .build();
 *         
 *         scheduler.scheduleJob(job, trigger);
 * <pre>
 *  
 * @see JobBuilder
 * @see ScheduleBuilder
 * @see DateBuilder 
 * @see Trigger
 */
public class TriggerBuilder<T extends Trigger> {

    private TriggerKey key;
    private String description;
    private Date startTime = new Date();
    private Date endTime;
    private int priority = Trigger.DEFAULT_PRIORITY;
    private String calendarName;
    private JobKey jobKey;
    private JobDataMap jobDataMap = new JobDataMap();
    
    private ScheduleBuilder<?> scheduleBuilder = null;
    
    private TriggerBuilder() {
        
    }
    

    public static TriggerBuilder<Trigger> newTrigger() {
        return new TriggerBuilder<Trigger>();
    }
    
    /**
     * 构建对应Trigger
     */
    @SuppressWarnings("unchecked")
    public T build() {

        if(scheduleBuilder == null)
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        MutableTrigger trig = scheduleBuilder.build();
        
        trig.setCalendarName(calendarName);
        trig.setDescription(description);
        trig.setStartTime(startTime);
        trig.setEndTime(endTime);
        if(key == null)
            key = new TriggerKey(Key.createUniqueName(null), null);
        trig.setKey(key); 
        if(jobKey != null)
            trig.setJobKey(jobKey);
        trig.setPriority(priority);
        
        if(!jobDataMap.isEmpty())
            trig.setJobDataMap(jobDataMap);
        
        return (T) trig;
    }

    public TriggerBuilder<T> withIdentity(String name) {
        key = new TriggerKey(name, null);
        return this;
    }  

    public TriggerBuilder<T> withIdentity(String name, String group) {
        key = new TriggerKey(name, group);
        return this;
    }

    public TriggerBuilder<T> withIdentity(TriggerKey triggerKey) {
        this.key = triggerKey;
        return this;
    }

    public TriggerBuilder<T> withDescription(String triggerDescription) {
        this.description = triggerDescription;
        return this;
    }

    public TriggerBuilder<T> withPriority(int triggerPriority) {
        this.priority = triggerPriority;
        return this;
    }

    public TriggerBuilder<T> modifiedByCalendar(String calName) {
        this.calendarName = calName;
        return this;
    }

    public TriggerBuilder<T> startAt(Date triggerStartTime) {
        this.startTime = triggerStartTime;
        return this;
    }

    public TriggerBuilder<T> startNow() {
        this.startTime = new Date();
        return this;
    }

    public TriggerBuilder<T> endAt(Date triggerEndTime) {
        this.endTime = triggerEndTime;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <SBT extends T> TriggerBuilder<SBT> withSchedule(ScheduleBuilder<SBT> schedBuilder) {
        this.scheduleBuilder = schedBuilder;
        return (TriggerBuilder<SBT>) this;
    }

    public TriggerBuilder<T> forJob(JobKey keyOfJobToFire) {
        this.jobKey = keyOfJobToFire;
        return this;
    }
    

    public TriggerBuilder<T> forJob(String jobName) {
        this.jobKey = new JobKey(jobName, null);
        return this;
    }
    

    public TriggerBuilder<T> forJob(String jobName, String jobGroup) {
        this.jobKey = new JobKey(jobName, jobGroup);
        return this;
    }

    public TriggerBuilder<T> forJob(JobDetail jobDetail) {
        JobKey k = jobDetail.getKey();
        if(k.getName() == null)
            throw new IllegalArgumentException("The given job has not yet had a name assigned to it.");
        this.jobKey = k;
        return this;
    }


    public TriggerBuilder<T> usingJobData(String dataKey, String value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(String dataKey, Integer value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(String dataKey, Long value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(String dataKey, Float value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(String dataKey, Double value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(String dataKey, Boolean value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public TriggerBuilder<T> usingJobData(JobDataMap newJobDataMap) {
        // add any existing data to this new map
        for(String dataKey: jobDataMap.keySet()) {
            newJobDataMap.put(dataKey, jobDataMap.get(dataKey));
        }
        jobDataMap = newJobDataMap; // set new map as the map to use
        return this;
    }
    
}
