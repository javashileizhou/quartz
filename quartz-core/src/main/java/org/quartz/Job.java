
/* 
 * Copyright 2001-2009 Terracotta, Inc. 
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

/**
 * <p>
 * The interface to be implemented by classes which represent a 'job' to be
 * performed.
 * </p>
 * 
 * <p>
 * Instances of <code>Job</code> must have a <code>public</code>
 * no-argument constructor.
 * </p>
 * 
 * <p>
 * <code>JobDataMap</code> provides a mechanism for 'instance member data'
 * that may be required by some implementations of this interface.
 * </p>
 * 
 * @see JobDetail
 * @see JobBuilder
 * @see ExecuteInJTATransaction
 * @see DisallowConcurrentExecution
 * @see PersistJobDataAfterExecution
 * @see Trigger
 * @see Scheduler
 * 
 * @author James House
 */
public interface Job {

    /**
     * 定时任务执行逻辑
     */
    void execute(JobExecutionContext context)
        throws JobExecutionException;

}
