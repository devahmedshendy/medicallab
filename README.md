# MedicalLab
MedicalLab is a Simple Java-Based Web App Using Spring Framework

### Demo - release/branch dev-1.0.0
- Online: [medicallab-mvc](http://medicallab-mvc.herokuapp.com)
  - Users
    - role: Investigation Doctor
      - username: doctor
      - password: 123456
    - role: Registration Officer
      - username: officer
      - password: 123456

- Offline (Eclipse): [dev-1.0.0](https://github.com/devahmedshendy/medicallab/tree/dev-1.0.0)
  - Database (MySQL)
    - username: root
    - password: root
    - dump file: [dev-1.0.0.sql](https://github.com/devahmedshendy/projects-metadata/blob/master/medicallab/datamodel/dev-1.0.0.sql)
    
  - Users
    - role: Investigation Doctor
      - username: doctor
      - password: 123456
    - role: Registration Officer
      - username: officer
      - password: 123456
  
    

### Features Overview - release/branch dev-1.0.0
> NOTE **1**: You might find a feature that doesn't make sense. Features has been added to satisfy this __Spring framekwork can do this, let apply it with fake/non-logic requirement__.

> NOTE **2**: Old browsers might not render the website well. Updating browsers to latest release would also provide you with security/bug fixes and more features/enhancemenst.

- /login Page - User Login
  * No regisration avaialble in this app, business need come from this project [citc_lab](https://github.com/devahmedshendy/citc_lab), admin user prepare a user account for you.

- /login Page - Get Patient Medical Profile
  * Currently (as a public user) you can obtain medical profile for patient using his personal id.

- /users Page
  * Only admin users are allowed to access this page.
  * Admin user can create/update/delete/enable-or-disable any other non-admin user.
  * Admin user can update his settings through /me page.

- /me Page
  * User can change his settings and password too.

- /patients Page
  * Only officer user can create/update/delete patient.
  * Any user (public or private) can view medical profile of specific patient
    * Medical profile has the full details regarding patient and all his submitted test details
    > NOTE: In this release dev-1.0.0, tests/requests feature are not implemented yet, thus medical profile page doesn't reflect stored data right now. They will be addded in the next release.

- Search/Pagination
  * In any page (/users, /patients, ...etc)
  * 10 users per page.
  * You can sort by clicking on column names.
  * You can search using available search options.
  * You can paginate through search results with any sort.



### Core Components - release/branch dev-1.0.0
- Spring Framework Modules: 
  - Spring-MVC, Spring-ORM, Spring-Session: 4.3.10.RELEASE
  - Spring-Security, Spring-Taglibs: 4.2.3.RELEASE
  
- Others:
  - Hibernate 5, MySQL 5, JSP, Apache Tiles 3, Jackson2
  - jQuery v3.2.1, Bootstrap v4.0, font-awesome v4.7.0, URI v1.18.12, Qwest v4.5.0



### Customization Overview - release/branch dev-1.0.0
- Using Apache Tiles
  - Configure tiles through tilesConfigurer bean
  - Create view resolver through tilesViewResolver bean

- Using Message Source
  - Configure spring to use messages.properties as a messages source
    through messageSource bean

- Resource Handler
  - Customize it through overriding addResourceHandlers() method
  - Set cache period
  - Disable resource chain
  - Create VersionResourceResolver and apply ContentVersionStrategy as versioning strategy, which uses a fingerprinting technique to add hash to the name of the file that matches the checksum of the file content
    ex: app-82746c47f5d0ee02b58c4bc43941bcba.js, new changes would change the name of the file so browser would make new request instead of using the current cached one

- Hibnerate
  - Setting hibernate properties through hibernateProperties bean
  - C3P0 is a third party connection pool suits production than the internal connection pool of Hibernate
    I used to to minimize the number of connection pool size to use it to be deployed on heroku and uses very low free cloud resource (such as database)
  - Using MySQL as Hibernate dialect
  - Configure basic data source for mysql driver through dataSource bean

- Security Configuration
  - I needed to have control over users session so that I can force users to logout because some settings have changed (ex: password, username), enable max session per user and so on 
    As declare in my answer here: http://bit.ly/2xGULLy, I have to enable HttpSessionEventPublisher, and provide sessionRegistry bean
    Then configure sessionManagement() of HttpSecurity instance with my needs
  - Encrypt users password using bcryptPasswordEncoder bean
  - Apply role based security to URLs
  - Apply custom accessDeniedHandler
  - Apply custom login form
  - Customize beforeSpringSecurityFilterChain to insert the multipart filter before SpringSecurityFilterChain

- UserDetails
  - Did some customization to fit the needs of users table then configure AuthenticationManagerBuilder to use the custom UserDetails which in my case User.java
