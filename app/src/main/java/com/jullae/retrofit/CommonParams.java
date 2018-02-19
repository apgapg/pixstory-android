package com.jullae.retrofit;

import java.util.HashMap;

/**
 * Developer: Saurabh Verma
 * Dated: 27-09-2016.
 */
public final class CommonParams {
    private HashMap<String, String> map = new HashMap<>();

    /**
     * Constructor
     *
     * @param builder object of builder class of CommonParams
     */
    private CommonParams(final Builder builder) {
        this.map = builder.map;
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public HashMap<String, String> getMap() {
        return map;
    }


    /**
     * The type Builder.
     */
    public static class Builder {
        /**
         * The Map.
         */
        private HashMap<String, String> map = new HashMap<>();

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
        }


        /**
         * Add builder.
         *
         * @param key   the key
         * @param value the value
         * @return the builder
         */
        public Builder add(final String key, final Object value) {
            if (value == null || String.valueOf(value).isEmpty()) {
                return this;
            }
            map.put(key, String.valueOf(value));
            return this;
        }

        /**
         * Add builder.
         *
         * @param key          the key
         * @param value        the value
         * @param isAllowEmpty the is allow empty
         * @return the builder
         */
        public Builder add(final String key, final Object value, final boolean isAllowEmpty) {
            if (!isAllowEmpty && (value == null || String.valueOf(value).isEmpty())) {
                return this;
            }
            map.put(key, String.valueOf(value));
            return this;
        }


        /**
         * Build common params.
         *
         * @return the common params
         */
        public CommonParams build() {
            return new CommonParams(this);
        }
    }
}


