/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.its.test.rules.drools;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * description: 
 * company: tzz
 * @author: tzz
 * date: 2019/08/26 11:34
 */
public class DroolsTest {

    public static final void main(final String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        execute(kc);
    }

    public static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("HelloWorldKS");
        // 设置全局变量
        ksession.setGlobal("list", new ArrayList<Object>());
        // 设置监听器
        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugRuleRuntimeEventListener());
        final Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        ksession.insert(message);

        ksession.fireAllRules();
        ksession.dispose();
    }

    public static class Message {
        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public Message() {

        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(final int status) {
            this.status = status;
        }

        public static Message doSomething(Message message) {
            return message;
        }

        public boolean isSomething(String msg, List<Object> list) {
            list.add(this);
            return this.message.equals(msg);
        }
    }

}
