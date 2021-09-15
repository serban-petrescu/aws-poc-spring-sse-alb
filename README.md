# Spring SSE behind AWS ALB PoC

Proof-of-concept that checks if Server Sent Events work with a Spring Boot App behind an AWS Application Load Balancer.

Main components:
 * The ALB + ECS Fargate service is set up via CDK in `/bin/checkin-sse-alb.ts`.
 * The Spring Boot backend is in `/app`.
 * The backend `DemoSecurity` configuration creates a form-based login with 2 users: user1 / password, user2 / password.
 * The backend `DemoService` service maintains a map between user IDs (usernames) and SSE Emitters.
 * The backend `DemoController` controller exposes an endpoint that is called by the UI to start a SSE connection.
 * The HTML frontend is in `/app/src/main/resources/static`.
 * Once opened, the frontend immediately starts the SSE connection and prints the messages received.
