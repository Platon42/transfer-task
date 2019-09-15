package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@DatabaseTable(tableName = "CLIENT")
@Entity
@Table(name = "CLIENT", schema = "TRANSFER")
public class ClientEntity {

    @DatabaseField(generatedId = true)
    private int clientId;

    @DatabaseField(columnName = "FIRST_NAME")
    private String firstName;

    @DatabaseField(columnName = "LAST_NAME")
    private String lastName;

    @DatabaseField(columnName = "MIDDLE_NAME")
    private String middleName;

    @DatabaseField(columnName = "BIRTHDAY")
    private Date birthday;

    @DatabaseField(columnName = "SEX")
    private int sex;

    @DatabaseField(columnName = "RESIDENT_COUNTRY")
    private String residentCountry;

    @Id
    @Column(name = "CLIENT_ID")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "BIRTHDAY")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "SEX")
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "RESIDENT_COUNTRY")
    public String getResidentCountry() {
        return residentCountry;
    }

    public void setResidentCountry(String residentCountry) {
        this.residentCountry = residentCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return clientId == that.clientId &&
                sex == that.sex &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(residentCountry, that.residentCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, firstName, lastName, middleName, birthday, sex, residentCountry);
    }
}
