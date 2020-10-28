package si.fri.prpo.skupina33.jdbc;

import java.io.Serializable;

public abstract class Entiteta implements Serializable {

    private static final long serialVersionUID = 4188346321092041162L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
