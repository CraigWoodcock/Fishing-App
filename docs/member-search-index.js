memberSearchIndex = [{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Role","l":"ADMIN"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"angler"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"angler"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"Angler()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"Angler(Long, String, Set<Catch>, Set<AnglerSession>)","u":"%3Cinit%3E(java.lang.Long,java.lang.String,java.util.Set,java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"anglerId"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"anglerRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"anglerRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"anglerRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"anglerService"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"AnglerService(AnglerRepository, AnglerSessionRepository, SessionRepository)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.repository.AnglerRepository,com.craigwoodcock.fishingapp.repository.AnglerSessionRepository,com.craigwoodcock.fishingapp.repository.SessionRepository)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"AnglerSession()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"AnglerSessionId()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"AnglerSessionId(Long, Long)","u":"%3Cinit%3E(java.lang.Long,java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"anglerSessionRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"anglerSessionRepository"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"anglerSessions"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"anglerSessions"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"ApiErrorResponse()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"ApiErrorResponse(int, String)","u":"%3Cinit%3E(int,java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"apiSecurityFilterChain(HttpSecurity)","u":"apiSecurityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"AuthApiController(UserService, JwtUtils, PasswordEncoder)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.UserService,com.craigwoodcock.fishingapp.utils.JwtUtils,org.springframework.security.crypto.password.PasswordEncoder)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"AuthController","l":"AuthController()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"AuthRequest()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"AuthResponse(String, String)","u":"%3Cinit%3E(java.lang.String,java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"Catch()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"catches"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"catches"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"catchTime"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAuthenticationEntryPoint","l":"commence(HttpServletRequest, HttpServletResponse, AuthenticationException)","u":"commence(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse,org.springframework.security.core.AuthenticationException)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"createAnglerSession(Session, Angler)","u":"createAnglerSession(com.craigwoodcock.fishingapp.model.entity.Session,com.craigwoodcock.fishingapp.model.entity.Angler)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"createdAt"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"createdAt"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"createdAt"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"createSession(String, LocalDate, int, String, Authentication, RedirectAttributes)","u":"createSession(java.lang.String,java.time.LocalDate,int,java.lang.String,org.springframework.security.core.Authentication,org.springframework.web.servlet.mvc.support.RedirectAttributes)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"createSession(User, String, LocalDate, int, String)","u":"createSession(com.craigwoodcock.fishingapp.model.entity.User,java.lang.String,java.time.LocalDate,int,java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"customAccessDeniedHandler()"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAccessDeniedHandler","l":"CustomAccessDeniedHandler(ObjectMapper)","u":"%3Cinit%3E(com.fasterxml.jackson.databind.ObjectMapper)"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"customAuthenticationEntryPoint()"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAuthenticationEntryPoint","l":"CustomAuthenticationEntryPoint(ObjectMapper)","u":"%3Cinit%3E(com.fasterxml.jackson.databind.ObjectMapper)"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"customUserDetailsService"},{"p":"com.craigwoodcock.fishingapp.service","c":"CustomUserDetailsService","l":"CustomUserDetailsService()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"Dashboardcontroller","l":"Dashboardcontroller(UserService, SessionService, AnglerSessionRepository)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.UserService,com.craigwoodcock.fishingapp.service.SessionService,com.craigwoodcock.fishingapp.repository.AnglerSessionRepository)"},{"p":"com.craigwoodcock.fishingapp.utils","c":"DateFormatter","l":"DATE_TIME_FORMATTER"},{"p":"com.craigwoodcock.fishingapp.utils","c":"DateFormatter","l":"DateFormatter()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.repository","c":"AnglerSessionRepository","l":"deleteBySessionId(Long)","u":"deleteBySessionId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"deleteSession(Long)","u":"deleteSession(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"deleteSession(Long, RedirectAttributes)","u":"deleteSession(java.lang.Long,org.springframework.web.servlet.mvc.support.RedirectAttributes)"},{"p":"com.craigwoodcock.fishingapp.security","c":"JwtAuthenticationFilter","l":"doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)","u":"doFilterInternal(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse,jakarta.servlet.FilterChain)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"durationHours"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"editSessionForm(Long, Model)","u":"editSessionForm(java.lang.Long,org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"email"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"email"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"equals(Object)","u":"equals(java.lang.Object)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"UserRepository","l":"existsByUsername(String)","u":"existsByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"EXPIRATION_TIME"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"expiryDate"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"extractAllClaims(String)","u":"extractAllClaims(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"findAllAnglersByUser(User)","u":"findAllAnglersByUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"findAllUsers()"},{"p":"com.craigwoodcock.fishingapp.repository","c":"CatchRepository","l":"findByAngler(Angler)","u":"findByAngler(com.craigwoodcock.fishingapp.model.entity.Angler)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"UserRepository","l":"findByEmail(String)","u":"findByEmail(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"AnglerRepository","l":"findById(long)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"findById(Long)","u":"findById(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"AnglerRepository","l":"findByName(String)","u":"findByName(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"CatchRepository","l":"findBySession(Session)","u":"findBySession(com.craigwoodcock.fishingapp.model.entity.Session)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"AnglerSessionRepository","l":"findBySessionId(Long)","u":"findBySessionId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"CatchRepository","l":"findBySessionId(Long)","u":"findBySessionId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"AnglerSessionRepository","l":"findBySessionUserId(Long)","u":"findBySessionUserId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"JwtTokenRepository","l":"findByToken(String)","u":"findByToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"SessionRepository","l":"findByUser(User)","u":"findByUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"JwtTokenRepository","l":"findByUserAndRevokedFalse(User)","u":"findByUserAndRevokedFalse(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.repository","c":"UserRepository","l":"findByUsername(String)","u":"findByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"findByUsername(String)","u":"findByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"findOrCreateAngler(String)","u":"findOrCreateAngler(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp","c":"FishingAppApplication","l":"FishingAppApplication()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"fishType"},{"p":"com.craigwoodcock.fishingapp.utils","c":"DateFormatter","l":"formatLocalDateTime(LocalDateTime)","u":"formatLocalDateTime(java.time.LocalDateTime)"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"generateToken(String)","u":"generateToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"getAllSessionsByUser(User)","u":"getAllSessionsByUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"getAllUsers()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"getAngler()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getAngler()"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"getAnglerId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"getAnglerSessions()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getAnglerSessions()"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"getAnglersForSession(Long)","u":"getAnglersForSession(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getAuthorities()"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"getById(Long)","u":"getById(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"getByUsername(String)","u":"getByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"getCatches()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getCatches()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getCatchTime()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getCreatedAt()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"getCreatedAt()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getCreatedAt()"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"Dashboardcontroller","l":"getDashboardScreen(Model, Authentication)","u":"getDashboardScreen(org.springframework.ui.Model,org.springframework.security.core.Authentication)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getDurationHours()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getEmail()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getEmail()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"getExpiryDate()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getFishType()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getId()"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"IndexController","l":"getIndexPage()"},{"p":"com.craigwoodcock.fishingapp.security","c":"JwtAuthenticationFilter","l":"getJwtFromRequest(HttpServletRequest)","u":"getJwtFromRequest(jakarta.servlet.http.HttpServletRequest)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"AuthController","l":"getLoginForm(User, String, String, Model)","u":"getLoginForm(com.craigwoodcock.fishingapp.model.entity.User,java.lang.String,java.lang.String,org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"getMessage()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getName()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"getName()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getName()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getNotes()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"getPassword()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getPassword()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getPegOrSwim()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getPhotoUrl()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"getRevoked()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getRole()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getRole()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"getSession()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getSession()"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"getSessionById(long)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"getSessionId()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getSessions()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getStartDate()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"getStatus()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"getTimestamp()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"getToken()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"getToken()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getUpdatedAt()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getUpdatedAt()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"getUser()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getUser()"},{"p":"com.craigwoodcock.fishingapp.repository","c":"UserRepository","l":"getUserById(long)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"UserApiController","l":"getUserById(Long)","u":"getUserById(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"UserApiController","l":"getUserByUsername(String)","u":"getUserByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"getUsername()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"getUsername()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"getUsername()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"getUsername()"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"getUsernameFromToken(String)","u":"getUsernameFromToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"UserApiController","l":"getUsers()"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"getUserTokens(User)","u":"getUserTokens(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"getVenue()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"getWeight()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"GlobalApiExceptionHandler()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalWebExceptionHandler","l":"GlobalWebExceptionHandler()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAccessDeniedHandler","l":"handle(HttpServletRequest, HttpServletResponse, AccessDeniedException)","u":"handle(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse,org.springframework.security.access.AccessDeniedException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"handleForbidden(UserForbiddenException)","u":"handleForbidden(com.craigwoodcock.fishingapp.exception.UserForbiddenException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalWebExceptionHandler","l":"handleForbidden(UserForbiddenException)","u":"handleForbidden(com.craigwoodcock.fishingapp.exception.UserForbiddenException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"handleGenericError(Exception)","u":"handleGenericError(java.lang.Exception)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalWebExceptionHandler","l":"handleGenericError(Exception)","u":"handleGenericError(java.lang.Exception)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"handleUnauthorized(UserUnauthorizedException)","u":"handleUnauthorized(com.craigwoodcock.fishingapp.exception.UserUnauthorizedException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalWebExceptionHandler","l":"handleUnauthorized(UserUnauthorizedException)","u":"handleUnauthorized(com.craigwoodcock.fishingapp.exception.UserUnauthorizedException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"handleUserAlreadyExists(UserAlreadyExistsException)","u":"handleUserAlreadyExists(com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalApiExceptionHandler","l":"handleUserNotFound(UserNotFoundException)","u":"handleUserNotFound(com.craigwoodcock.fishingapp.exception.UserNotFoundException)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"GlobalWebExceptionHandler","l":"handleUserNotFoundException(UserNotFoundException)","u":"handleUserNotFoundException(com.craigwoodcock.fishingapp.exception.UserNotFoundException)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"hashCode()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"id"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"id"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"IndexController","l":"IndexController()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp","c":"FishingAppApplication","l":"init(UserRepository, PasswordEncoder)","u":"init(com.craigwoodcock.fishingapp.repository.UserRepository,org.springframework.security.crypto.password.PasswordEncoder)"},{"p":"com.craigwoodcock.fishingapp.security","c":"JwtAuthenticationFilter","l":"JwtAuthenticationFilter(CustomUserDetailsService, JwtUtils)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.CustomUserDetailsService,com.craigwoodcock.fishingapp.utils.JwtUtils)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"JwtToken()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"jwtTokenRepository"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"jwtUtils"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"jwtUtils"},{"p":"com.craigwoodcock.fishingapp.security","c":"JwtAuthenticationFilter","l":"jwtUtils"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"jwtUtils"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"JwtUtils()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.service","c":"CustomUserDetailsService","l":"loadUserByUsername(String)","u":"loadUserByUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"log"},{"p":"com.craigwoodcock.fishingapp","c":"FishingAppApplication","l":"log"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"log"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"log"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"log"},{"p":"com.craigwoodcock.fishingapp.utils","c":"DateFormatter","l":"log"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"login(AuthRequest)","u":"login(com.craigwoodcock.fishingapp.model.dto.AuthRequest)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"AuthController","l":"logout(RedirectAttributes)","u":"logout(org.springframework.web.servlet.mvc.support.RedirectAttributes)"},{"p":"com.craigwoodcock.fishingapp","c":"FishingAppApplication","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"message"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"name"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"name"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"name"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"newSessionForm(Model)","u":"newSessionForm(org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"notes"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"objectMapper"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAccessDeniedHandler","l":"objectMapper"},{"p":"com.craigwoodcock.fishingapp.security","c":"CustomAuthenticationEntryPoint","l":"objectMapper"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"password"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"password"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"passwordEncoder"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"passwordEncoder"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"passwordEncoder()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"pegOrSwim"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"photoUrl"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"UserController","l":"processRegistrationForm(User, BindingResult, Model)","u":"processRegistrationForm(com.craigwoodcock.fishingapp.model.entity.User,org.springframework.validation.BindingResult,org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"register(User)","u":"register(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"registerUser(User)","u":"registerUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"removeAngler(Long, Long)","u":"removeAngler(java.lang.Long,java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"removeAnglerFromSession(Long, Long)","u":"removeAnglerFromSession(java.lang.Long,java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"revoked"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"revokeToken(String)","u":"revokeToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"role"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"role"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Role","l":"Role()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"SECRET_KEY"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"SecurityConfig(CustomUserDetailsService, JwtUtils, ObjectMapper)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.CustomUserDetailsService,com.craigwoodcock.fishingapp.utils.JwtUtils,com.fasterxml.jackson.databind.ObjectMapper)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"session"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"session"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"Session()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"Session(Long, User, String, LocalDate, Integer, Set<Catch>, Set<AnglerSession>)","u":"%3Cinit%3E(java.lang.Long,com.craigwoodcock.fishingapp.model.entity.User,java.lang.String,java.time.LocalDate,java.lang.Integer,java.util.Set,java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"SessionController(SessionService, UserService, AnglerRepository)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.SessionService,com.craigwoodcock.fishingapp.service.UserService,com.craigwoodcock.fishingapp.repository.AnglerRepository)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"sessionId"},{"p":"com.craigwoodcock.fishingapp.service","c":"AnglerService","l":"sessionRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"sessionRepository"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"sessions"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"Dashboardcontroller","l":"sessionService"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"sessionService"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"SessionService(SessionRepository, AnglerRepository, AnglerSessionRepository, AnglerService)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.repository.SessionRepository,com.craigwoodcock.fishingapp.repository.AnglerRepository,com.craigwoodcock.fishingapp.repository.AnglerSessionRepository,com.craigwoodcock.fishingapp.service.AnglerService)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"setAngler(Angler)","u":"setAngler(com.craigwoodcock.fishingapp.model.entity.Angler)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setAngler(Angler)","u":"setAngler(com.craigwoodcock.fishingapp.model.entity.Angler)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"setAnglerId(Long)","u":"setAnglerId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"setAnglerSessions(Set<AnglerSession>)","u":"setAnglerSessions(java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setAnglerSessions(Set<AnglerSession>)","u":"setAnglerSessions(java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"setCatches(Set<Catch>)","u":"setCatches(java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setCatches(Set<Catch>)","u":"setCatches(java.util.Set)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setCatchTime(LocalDateTime)","u":"setCatchTime(java.time.LocalDateTime)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"setCreatedAt(Instant)","u":"setCreatedAt(java.time.Instant)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setCreatedAt(LocalDateTime)","u":"setCreatedAt(java.time.LocalDateTime)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setDurationHours(Integer)","u":"setDurationHours(java.lang.Integer)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setEmail(String)","u":"setEmail(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"setExpiryDate(LocalDateTime)","u":"setExpiryDate(java.time.LocalDateTime)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setFishType(String)","u":"setFishType(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"setId(AnglerSessionId)","u":"setId(com.craigwoodcock.fishingapp.model.id.AnglerSessionId)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"setId(Long)","u":"setId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setId(Long)","u":"setId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"setId(Long)","u":"setId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setId(Long)","u":"setId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setId(Long)","u":"setId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"setMessage(String)","u":"setMessage(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Angler","l":"setName(String)","u":"setName(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setName(String)","u":"setName(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setNotes(String)","u":"setNotes(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"setPassword(String)","u":"setPassword(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setPassword(String)","u":"setPassword(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setPegOrSwim(String)","u":"setPegOrSwim(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setPhotoUrl(String)","u":"setPhotoUrl(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"setRevoked(Boolean)","u":"setRevoked(java.lang.Boolean)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setRole(Role)","u":"setRole(com.craigwoodcock.fishingapp.model.entity.Role)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"AnglerSession","l":"setSession(Session)","u":"setSession(com.craigwoodcock.fishingapp.model.entity.Session)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setSession(Session)","u":"setSession(com.craigwoodcock.fishingapp.model.entity.Session)"},{"p":"com.craigwoodcock.fishingapp.model.id","c":"AnglerSessionId","l":"setSessionId(Long)","u":"setSessionId(java.lang.Long)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setSessions(List<Session>)","u":"setSessions(java.util.List)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setStartDate(LocalDate)","u":"setStartDate(java.time.LocalDate)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"setStatus(int)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"setTimestamp(long)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"setToken(String)","u":"setToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"setToken(String)","u":"setToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setUpdatedAt(LocalDateTime)","u":"setUpdatedAt(java.time.LocalDateTime)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"setUser(User)","u":"setUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setUser(User)","u":"setUser(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"setUsername(String)","u":"setUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"setUsername(String)","u":"setUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"setUsername(String)","u":"setUsername(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"setVenue(String)","u":"setVenue(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"setWeight(BigDecimal)","u":"setWeight(java.math.BigDecimal)"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"UserController","l":"showRegistrationForm(Model)","u":"showRegistrationForm(org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"startDate"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"status"},{"p":"com.craigwoodcock.fishingapp.exception","c":"ApiErrorResponse","l":"timestamp"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"token"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"token"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"toString()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"toString()"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"updatedAt"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"updatedAt"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"updateSession(Long, Session)","u":"updateSession(java.lang.Long,com.craigwoodcock.fishingapp.model.entity.Session)"},{"p":"com.craigwoodcock.fishingapp.service","c":"SessionService","l":"updateSession(Session)","u":"updateSession(com.craigwoodcock.fishingapp.model.entity.Session)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"JwtToken","l":"user"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"user"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Role","l":"USER"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"User()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.exception","c":"UserAlreadyExistsException","l":"UserAlreadyExistsException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"UserApiController","l":"UserApiController()","u":"%3Cinit%3E()"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"UserController","l":"UserController(UserService)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.service.UserService)"},{"p":"com.craigwoodcock.fishingapp.security","c":"JwtAuthenticationFilter","l":"userDetailsService"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"UserDto(User)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.model.entity.User)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"UserForbiddenException","l":"UserForbiddenException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthRequest","l":"username"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"AuthResponse","l":"username"},{"p":"com.craigwoodcock.fishingapp.model.dto","c":"UserDto","l":"username"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"User","l":"username"},{"p":"com.craigwoodcock.fishingapp.exception","c":"UserNotFoundException","l":"UserNotFoundException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.service","c":"CustomUserDetailsService","l":"userRepository"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"userRepository"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"AuthApiController","l":"userService"},{"p":"com.craigwoodcock.fishingapp.controller.apiController","c":"UserApiController","l":"userService"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"Dashboardcontroller","l":"userService"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"userService"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"UserController","l":"userService"},{"p":"com.craigwoodcock.fishingapp.service","c":"UserService","l":"UserService(UserRepository, PasswordEncoder, JwtUtils, JwtTokenRepository)","u":"%3Cinit%3E(com.craigwoodcock.fishingapp.repository.UserRepository,org.springframework.security.crypto.password.PasswordEncoder,com.craigwoodcock.fishingapp.utils.JwtUtils,com.craigwoodcock.fishingapp.repository.JwtTokenRepository)"},{"p":"com.craigwoodcock.fishingapp.exception","c":"UserUnauthorizedException","l":"UserUnauthorizedException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.utils","c":"JwtUtils","l":"validateToken(String)","u":"validateToken(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Role","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Role","l":"values()"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Session","l":"venue"},{"p":"com.craigwoodcock.fishingapp.controller.webController","c":"SessionController","l":"viewSession(Long, Model)","u":"viewSession(java.lang.Long,org.springframework.ui.Model)"},{"p":"com.craigwoodcock.fishingapp.config","c":"SecurityConfig","l":"webSecurityFilterChain(HttpSecurity)","u":"webSecurityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity)"},{"p":"com.craigwoodcock.fishingapp.model.entity","c":"Catch","l":"weight"}];updateSearchResults();