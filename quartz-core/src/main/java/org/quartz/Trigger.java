
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

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;



/**
 * @see TriggerBuilder
 * @see JobDataMap
 * @see JobExecutionContext
 * @see TriggerUtils
 * @see SimpleTrigger
 * @see CronTrigger
 * @see CalendarIntervalTrigger
 * 
 * @author James House
 */
public interface Trigger extends Serializable, Cloneable, Comparable<Trigger> {

    long serialVersionUID = -3904243490805975570L;
    
    enum TriggerState { NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED }
    

    enum CompletedExecutionInstruction { NOOP, RE_EXECUTE_JOB, SET_TRIGGER_COMPLETE, DELETE_TRIGGER,
        SET_ALL_JOB_TRIGGERS_COMPLETE, SET_TRIGGER_ERROR, SET_ALL_JOB_TRIGGERS_ERROR }


    int MISFIRE_INSTRUCTION_SMART_POLICY = 0;
    

    int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
    
    /**
     * 默认策略
     */
    int DEFAULT_PRIORITY = 5;

    /**
     * 获取trigger Key
     */
    TriggerKey getKey();

    /**
     * 获取job Key
     */
    JobKey getJobKey();

    /**
     * 辅助性方法（可省略）
     */
    String getDescription();

    /**
     * 获取日历名字
     */
    String getCalendarName();

    /**
     * 获取任务数据内容
     */
    JobDataMap getJobDataMap();

    /**
     * 获取策略
     */
    int getPriority();

    /**
     * 是否可以再次运行job任务
     */
    boolean mayFireAgain();

    /**
     * 获取job任务运行时间
     */
    Date getStartTime();

    /**
     * 获取job任务结束时间
     */
    Date getEndTime();

    /**
     * 获取job下一次任务运行时间
     */
    Date getNextFireTime();

    /**
     * 获取job上一次任务运行时间
     */
    Date getPreviousFireTime();

    /**
     * 获取job某一个时间点之后的运行时间
     */
    Date getFireTimeAfter(Date afterTime);


    /**
     * 获取最后一次运行时间，如果永久运行返回为null
     */
    Date getFinalFireTime();

    /**
     * 获取错失发布的策略
     */
    int getMisfireInstruction();

    /**
     * 获取触发装置的构造器
     */
    TriggerBuilder<? extends Trigger> getTriggerBuilder();

    /**
     * 获取任务的构造器
     */
    ScheduleBuilder<? extends Trigger> getScheduleBuilder();


    boolean equals(Object other);

    int compareTo(Trigger other);

    /**
     * 获取最近运行时间的比较器，如果运行时间相同，则根据触发器的策略priority，越高越快，如果一样，则根据key排序
     */
    class TriggerTimeComparator implements Comparator<Trigger>, Serializable {
      
        private static final long serialVersionUID = -3904243490805975570L;
        
        // This static method exists for comparator in TC clustered quartz
        public static int compare(Date nextFireTime1, int priority1, TriggerKey key1, Date nextFireTime2, int priority2, TriggerKey key2) {
            if (nextFireTime1 != null || nextFireTime2 != null) {
                if (nextFireTime1 == null) {
                    return 1;
                }

                if (nextFireTime2 == null) {
                    return -1;
                }

                if(nextFireTime1.before(nextFireTime2)) {
                    return -1;
                }

                if(nextFireTime1.after(nextFireTime2)) {
                    return 1;
                }
            }

            int comp = priority2 - priority1;
            if (comp != 0) {
                return comp;
            }

            return key1.compareTo(key2);
        }

        @Override
        public int compare(Trigger t1, Trigger t2) {
            return compare(t1.getNextFireTime(), t1.getPriority(), t1.getKey(), t2.getNextFireTime(), t2.getPriority(), t2.getKey());
        }
    }
}
