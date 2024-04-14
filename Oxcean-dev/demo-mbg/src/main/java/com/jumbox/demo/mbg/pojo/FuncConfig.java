package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`func_config`")
public class FuncConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "func_name")
    private String funcName;

    @Column(name = "func_value")
    private Integer funcValue;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return func_name
     */
    public String getFuncName() {
        return funcName;
    }

    /**
     * @param funcName
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    /**
     * @return func_value
     */
    public Integer getFuncValue() {
        return funcValue;
    }

    /**
     * @param funcValue
     */
    public void setFuncValue(Integer funcValue) {
        this.funcValue = funcValue;
    }
}