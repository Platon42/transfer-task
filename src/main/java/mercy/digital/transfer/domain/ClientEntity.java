package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@DatabaseTable(tableName = "CLIENT")
@Entity
@Table(name = "CLIENT", schema = "TRANSFER", catalog = "H2")
public class ClientEntity {
    @DatabaseField(generatedId = true, columnName = "CLIENT_ID")
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
    @ForeignCollectionField(eager = true)
    private Collection<ClientAccountEntity> clientAccountsByClientId;

    @Id
    @Column(name = "CLIENT_ID", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "MIDDLE_NAME", nullable = true, length = 50)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "BIRTHDAY", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "SEX", nullable = false)
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "RESIDENT_COUNTRY", nullable = false, length = 20)
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

    @OneToMany(mappedBy = "clientByClientId")
    public Collection<ClientAccountEntity> getClientAccountsByClientId() {
        return clientAccountsByClientId;
    }

    public void setClientAccountsByClientId(Collection<ClientAccountEntity> clientAccountsByClientId) {
        this.clientAccountsByClientId = clientAccountsByClientId;
    }
}
