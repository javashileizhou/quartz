
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
 * The interface to be implemented by classes that want to be informed when a
 * <code>{@link org.quartz.JobDetail}</code> executes. In general,
 * applications that use a <code>Scheduler</code> will not have use for this
 * mechanism.
 * 
 * @see ListenerManager#addJobListener(JobListener, Matcher)
 * @see Matcher
 * @see Job
 * @see JobExecutionContext
 * @see JobExecutionException
 * @see TriggerListener
 * 
 * @author James House
 */
public interface JobListener {

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * 获取监听器名称
     */
    String getName();

    /**
     * （1）
     * 任务执行之前执行
     */
    void jobToBeExecuted(JobExecutionContext context);


     /**
      * (2)
      * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
      * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
      */
    void jobExecutionVetoed(JobExecutionContext context);


    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     */
    void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException);

}
