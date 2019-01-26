# Authentication
- problems with HTTP basic requires validation on every request. ur passing credentials every request! bad. more surface area. plus if u r encoding ur passwords properly it should take half a second to a second. which means this is slow. in authentication world u'd normally exchange long term credentials for a short term credentials. it takes longer to rotate username/password than a short term credential. also, people often reuse user/passwords so if its compromised then it has a larger impact than a short term creential which is secure random generated. SO, use some other mechanism. u use http basic to transport user/pw and exchange it for short term credentials. this is what youre doing in a form login. the other bad thing about http basic in a browser is that u can't logout. the only way to get out of the lopo is to close the whole browser. so, well use a proper authentication mechanism like a form login.

- why user details? its har to get password encoding correct. u want to make sure u get password encoding correct. u want to make sure u get all the details correct. if the user doesnt exist we dont want to reveal the user that is missing. there are side channel attacks like figuring out if the user exists based on how long it takes to process the request. if a user does exist, given what we know about password encoding, it should take something like .5 -1s. but naive code (from the pOV of security) might take much less time for somebody who DOESNT exist, because there's no password to validate. the correct performance engineering approach here, to return quickly, would be the wrong thing to do from a security pov. if the user doesnt exist, Spring Security has a dummy password that we always compare against.  also, we want to avoid session fixation attacks by changing the JSESSIONID on login (when we promote a regular http session to an authenticated session). we also need to rotate the csrf token.

- LDAP: dn = domain name (a hierarchy to find the current user, or anythign in LDAP). LDAP is a tree structure. `uid={0},ou=people` says find a UID leaf under the people branch. user info is in one tree, and groups (roles/autorities) are stored in another. so we must psecify both queries. `ou=groups` does that. `ldap://localhost:8389/dc=springframework,dc=org` is the base URL for both groups and users. eg: `dc=springframework,dc=org,ou=people,uid=....` there are two type sof auth in ldap: BIND authentication. BIND is that whateve the user types for user and password u try to LDAP bind by presenting user/pw to directory an i tells u if the user/pw is correct. the other type, LDAP search, is password comparison wherein we submit a query, get results, an compare PW in spring security client java code. So, auth happens in LDAP bind on the LDAP server and auth happens in the spring security client in second type. the LDAP server usually has a password encoding approach. there are specific fields for passwords. as an app developer we reately have the ability to 'write' passwords to LDAP, omore often need to read from LDAP that exists for other purporses. its been installed by the organiation and we have to reuse it. so, while we could a) side step the password field and write passwords that we encode into some other field and b) mae spring security aware of this change c) read the passwords and compare them in the client code, this is impractical. so we use things like `LdapShaPasswordEncoder` , which you can see is deprecated but NOT going anywhere. its a warning!

https://stackoverflow.com/questions/18756688/what-are-cn-ou-dc-in-an-ldap-search/18756876 
CN = Common Name
OU = Organizational Unit
DC = Domain Component
These are all parts of the X.500 Directory Specification, which defines nodes in a LDAP directory.

You can also read up on LDAP data Interchange Format (LDIF), which is an alternate format.

You read it from right to left, the right-most component is the root of the tree, and the left most component is the node (or leaf) you want to reach.

Each = pair is a search criteria.

With your example query

("CN=Dev-India,OU=Distribution Groups,DC=gp,DC=gl,DC=google,DC=com");
In effect the query is:




1. this is plain text. use NoOpPasswordEncoder.getInstance() ;
2. in order to take advantage of the new password encode in Sing security u need to migrate existing passwords.
3. if u are using plaint text that amounts to prefixing it whatever the appropirate prefix.
4. now replace NooOPWEncoder w/ DelegatingPWencoder
5. now imagine u have a signup page and the new user signs up and uses BCrypt. Use the DelegatingPWEncoder to encode new strings. the default is bcrypt.
6. NEW! spring security UDS supports password migration through the UserDetailsPasswordService. Out of the box only InMemoryUDM supports this. the idea is simple. we want them to login and SS
			and ideally to change their password.  SS will automatically check if the old password was stored in a insecure way and migrate to brcypt by invoking our UserDetailsPasswordService.
			All fo that logic is in the DaoAuthentiationProvider. It scostly to upgrade the encoding so we dont do this unless we need to
			But ur auth SHOULD support it. so the business/marketing could be any of a number of things. "weve changed terms of service. login." or "its been a month, password expired." or
			"we want to proactively protect u. taking additional measures."
7. there were two different PasswordEncoders in SS 4.2
8. if you are using the old password encoders u should see the migration javadocs in org.springframework.security.crypto.password.MessageDigestPasswordEncoder.


Custom Authentication: use a REST controller to handle login by setting the SecurityContext...*.
