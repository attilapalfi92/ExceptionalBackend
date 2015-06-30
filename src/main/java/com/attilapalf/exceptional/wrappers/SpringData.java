package com.attilapalf.exceptional.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by 212461305 on 2015.06.30..
 */
public class SpringData {
    private String type;
    private Value value;

    public SpringData() {
    }

    public SpringData(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    private static class Value {
        public Value() {
        }

        public Value(int id, String quote) {
            this.id = id;
            this.quote = quote;
        }

        private int id;
        private String quote;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }
    }
}
