# Job Referral System

A backend REST API for handling employee referrals at a company — basically the piece that sits between "I want a job here" and "someone on the inside actually vouches for me." Built it with Spring Boot, Spring Security + JWT, and JPA/Hibernate on MySQL.

## Why I built this

Most job portals only handle straight applications. The referral part — where an employee inside the company puts their name behind a candidate — is usually missing or hacked together. I wanted to model that properly: who can post a job, who can request a referral, who can approve it, and how that whole status lifecycle works.

There are 3 roles:
- **REQUESTER** – someone looking for a referral
- **REFERRER** – an employee who can post jobs and accept/reject referral requests
- **ADMIN** – for system-level stuff

## Stack

- Java 17 + Spring Boot
- Spring Security with JWT (stateless auth)
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok, Jakarta Bean Validation

## How it's structured

Pretty standard layered setup:

```
Controller → Service → Repository → Entity
```

Auth stuff lives in its own `security` package (JWT filter + token service), and Spring Security / CORS config is separated out into `config`. Kept it this way so business logic doesn't get tangled up with auth.

## Data model

- **User** — id, name, email (unique), password (hashed), role, resumeUrl
- **Job** — id, title, company, location, salary, postedBy (→ User)
- **ReferralRequest** — id, requester (→ User), referrer (→ User), job (→ Job), status (PENDING / ACCEPTED / REJECTED)

All the relationships have named FK constraints at the DB level, not just JPA-side.

## Auth flow

It's all stateless — no sessions. On login you get back a JWT, and every request after that just carries it as a Bearer token.

A custom `JwtAuthenticationFilter` checks the token on every request, pulls the user out, and sets up the Spring Security context so the rest of the app doesn't need to think about auth at all. Passwords are hashed with BCrypt. `/auth/**` is open for register/login, everything else needs a valid token.

## Endpoints

**Auth**
- `POST /auth/register` — register as a candidate
- `POST /auth/register/referrer` — register as a referrer
- `POST /auth/login` — get your JWT

**Jobs**
- `POST /jobs/createJob` — post a job
- `GET /jobs` — list jobs (paginated, optional search)

**Referrals**
- `POST /referrals` — request a referral
- `PATCH /referrals/{referralId}/status` — accept/reject a request
- `GET /referrals` — list referral requests (paginated)

**Users**
- `GET /users/{userId}/profile` — get a user's profile

List endpoints take `page` and `size` params and return everything wrapped in a `PagedResponseDto<T>`.

## A few decisions worth mentioning

- Entities never get exposed directly through the API — everything goes through DTOs, so the API contract isn't tied to how the DB is structured.
- Validation (`@Valid`) on all the write endpoints so junk doesn't even make it to the service layer.
- `@ManyToOne` relationships are all lazy-loaded, mainly to avoid N+1 issues on list endpoints.
- Stateless by design — should scale horizontally without any session-stickiness headaches.

## Running it locally

```bash
git clone https://github.com/Praveen18279/JobReferralSystem.git
cd JobReferralSystem
```

Set your DB creds and JWT secret/expiry in `application.properties`, then:

```bash
mvn clean install
mvn spring-boot:run
```

Runs on `localhost:8080`.

---

Built by Kenche Praveen — [LinkedIn](https://linkedin.com/in/praveen-kenche) · [GitHub](https://github.com/Praveen18279) · kenchepraveen9@gmail.com
