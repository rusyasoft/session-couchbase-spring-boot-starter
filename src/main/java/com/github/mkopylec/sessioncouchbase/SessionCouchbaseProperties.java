package com.github.mkopylec.sessioncouchbase;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Session couchbase configuration properties.
 */
@ConfigurationProperties("session-couchbase")
public class SessionCouchbaseProperties {

    /**
     * Properties responsible for persistent mode behaviour.
     */
    private Persistent persistent = new Persistent();
    /**
     * Properties responsible for in-memory mode behaviour.
     */
    private InMemory inMemory = new InMemory();

    public Persistent getPersistent() {
        return persistent;
    }

    public void setPersistent(Persistent persistent) {
        this.persistent = persistent;
    }

    public InMemory getInMemory() {
        return inMemory;
    }

    public void setInMemory(InMemory inMemory) {
        this.inMemory = inMemory;
    }

    public static class Persistent {

        /**
         * Couchbase cluster hosts.
         */
        private List<String> hosts = singletonList("localhost");
        /**
         * Couchbase bucket name where session data must be stored.
         */
        private String bucketName = "default";
        /**
         * Couchbase bucket password.
         */
        private String password = "";

        public List<String> getHosts() {
            return hosts;
        }

        public void setHosts(List<String> hosts) {
            this.hosts = hosts;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class InMemory {

        /**
         * Flag for enabling and disabling in-memory mode.
         */
        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
