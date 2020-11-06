package pl.edu.pg.eti.kask.blog.user.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Blog")
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "jdbc/Blog",
    callerQuery = "SELECT password FROM users WHERE username = ?",
    groupsQuery = "SELECT role FROM users_roles WHERE user = ?",
    hashAlgorithm = Pbkdf2PasswordHash.class
)
public class Config {
}
