package cn.itcast.nsfw.role.entity;

import java.util.Objects;
import java.util.Set;

public class Role {
    private String roleId;
    private String name;
    private String state;
    private Set<RolePrivilege> rolePrivileges;

    //角色状态
    public static String ROLE_STATE_VALID = "1";//有效
    public static String ROLE_STATE_INVALID = "0";//无效

    public Role() {
    }

    public Role(String roleId) {
        this.roleId=roleId;
    }

    public Role(String roleId, String name, String state, Set<RolePrivilege> rolePrivileges) {
        this.roleId = roleId;
        this.name = name;
        this.state = state;
        this.rolePrivileges = rolePrivileges;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) &&
                Objects.equals(name, role.name) &&
                Objects.equals(state, role.state) &&
                Objects.equals(rolePrivileges, role.rolePrivileges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name, state, rolePrivileges);
    }
}
