1. A Circuit Breaker(Prevents cascading failures with smart fallback mechanisms.) is a resilience pattern used in software systems to prevent repeated failures and allow graceful recovery when a service or component is failing.
A Circuit Breaker monitors interactions with a remote service. If the service fails repeatedly (e.g., due to timeouts or errors), the circuit "opens" to stop further calls and allow the system to recover. After a cooldown period, it tries again—if successful, it "closes" the circuit.
It helps avoid cascading failures and improves system stability.
How it works? -If more than 50% of the last 10 calls fail (failureRateThreshold), the circuit opens. -While open, calls are not made to the payment API, and the fallback method is called instead. -After 10 seconds, the circuit goes to half-open, lets a few calls through, and if they succeed, it closes again.

States:
Closed: Normal operation; requests go through.
Open: Requests are blocked to avoid overwhelming the failing service.
Half-Open: A few test requests are allowed to check if recovery is possible.
![image](https://github.com/user-attachments/assets/4ebf79b5-0175-44d4-9ea7-ee0dc6f65310)


2. A Retry is a fault-tolerance mechanism that automatically re-attempts a failed operation (like a remote service call), assuming the failure is temporary (e.g., network glitch or timeout).
How it works? -When a call fails (due to exception, timeout, etc.), the system waits (optional backoff) and tries again.  (This repeats for a defined maximum number of attempts), -If it still fails, it either throws the error or triggers another fallback mechanism (like a Circuit Breaker).

Common Retry Settings:
maxAttempts: How many times to retry.
waitDuration: Delay between retries.
retryExceptions: Which exceptions should trigger a retry.
ignoreExceptions: Exceptions that shouldn't trigger a retry.
Example -  Retrying a call to a flaky third-party API which sometimes fails due to temporary issues..

3. A Rate Limiter controls how many requests a system or service can handle in a given time window to prevent overload and ensure fair usage.
Why use it? -Prevent abuse or accidental overload (e.g., too many requests from a client). -Protect downstream services or APIs. -Ensure system stability and availability.
How it works? It limits the number of calls per second/minute/hour. Once the limit is hit: Additional requests are denied, delayed, or queued, depending on config.

Common Configurations:
limitForPeriod: Max number of calls allowed per period.
limitRefreshPeriod: Duration of each rate-limiting window.
timeoutDuration: How long to wait for a permit (if using a blocking limiter).
Example - Limiting a public REST API to 100 requests per minute per user.
