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

import org.quartz.impl.JobDetailImpl;
import org.quartz.utils.Key;


/**
 * 定时任务构造器，用于构造一个JobDetail
 */
public class JobBuilder {

    /**
     * 任务标识码，用于唯一确定任务
     */
    private JobKey key;
    private String description;
    private Class<? extends Job> jobClass;
    /**
     * Durability，持久性；如果Job是非持久性的，一旦没有Trigger与其相关联，
     * 它就会从Scheduler中被删除。也就是说Job的生命周期和其Trigger是关联的。
     */
    private boolean durability;
    /**
     * RequestsRecovery，如果为true，那么在Scheduler异常中止或者系统异常关闭后，当Scheduler重启后，Job会被重新执行。
     */
    private boolean shouldRecover;
    
    private JobDataMap jobDataMap = new JobDataMap();
    
    protected JobBuilder() {
    }

    public static JobBuilder newJob() {
        return new JobBuilder();
    }
    

    public static JobBuilder newJob(Class <? extends Job> jobClass) {
        JobBuilder b = new JobBuilder();
        b.ofType(jobClass);
        return b;
    }

    public JobDetail build() {

        JobDetailImpl job = new JobDetailImpl();
        
        job.setJobClass(jobClass);
        job.setDescription(description);
        if(key == null)
            key = new JobKey(Key.createUniqueName(null), null);
        job.setKey(key); 
        job.setDurability(durability);
        job.setRequestsRecovery(shouldRecover);
        
        
        if(!jobDataMap.isEmpty())
            job.setJobDataMap(jobDataMap);
        
        return job;
    }

    public JobBuilder withIdentity(String name) {
        key = new JobKey(name, null);
        return this;
    }  
    

    public JobBuilder withIdentity(String name, String group) {
        key = new JobKey(name, group);
        return this;
    }
    

    public JobBuilder withIdentity(JobKey jobKey) {
        this.key = jobKey;
        return this;
    }

    public JobBuilder withDescription(String jobDescription) {
        this.description = jobDescription;
        return this;
    }
    

    public JobBuilder ofType(Class <? extends Job> jobClazz) {
        this.jobClass = jobClazz;
        return this;
    }


    public JobBuilder requestRecovery() {
        this.shouldRecover = true;
        return this;
    }


    public JobBuilder requestRecovery(boolean jobShouldRecover) {
        this.shouldRecover = jobShouldRecover;
        return this;
    }

    public JobBuilder storeDurably() {
        this.durability = true;
        return this;
    }
    

    public JobBuilder storeDurably(boolean jobDurability) {
        this.durability = jobDurability;
        return this;
    }
    

    public JobBuilder usingJobData(String dataKey, String value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public JobBuilder usingJobData(String dataKey, Integer value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    public JobBuilder usingJobData(String dataKey, Long value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public JobBuilder usingJobData(String dataKey, Float value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public JobBuilder usingJobData(String dataKey, Double value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public JobBuilder usingJobData(String dataKey, Boolean value) {
        jobDataMap.put(dataKey, value);
        return this;
    }
    

    public JobBuilder usingJobData(JobDataMap newJobDataMap) {
        jobDataMap.putAll(newJobDataMap);
        return this;
    }


    public JobBuilder setJobData(JobDataMap newJobDataMap) {
        jobDataMap = newJobDataMap;
        return this;
    }
}
