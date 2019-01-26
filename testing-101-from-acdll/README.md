# Testing 101

## Grab Bag
- TDD basics
- spring-boot-starter-test (assertj, junit, mockito, )
- style of naming tests: `methodUnderTestWhenFooIsNullShouldThrowException()`
- `@MockBean`
- BDD w/ mockito when( mock.foo() ) .willReturn()..
- @Rule
  pubic ExpectedException exception = ExpectedException.none();
  expectation.thrown( SomeException.class )
- @DataJpaTest && TestEntityManager
- `MockResstServiceServer` (make sure u use the `RestTemplateBuilder` to create a RrestTemplate)
- compare and contrast this with WireMock
- @JsonTest and @Autowired JacksonTest<VehicleDetails> json
- Spring REST Docs
- spies vs mocks
- `WebMvcTest`
- how to setup integration tests in your maven build as a separate phase
- BDD - given / when / then  
- Selenium/WebDriver
- code coverage metrics

## Ordered Script

- XTreme Programming
 * https://airbrake.io/blog/sdlc/extreme-programming
- TDD Basics - there's a great video by [w/ Uncle Bob Martin](https://www.youtube.com/watch?v=qkblc5WRn-U):
  * 1) you're not allowed to write prod code unless it is to make a failing unit test pass
  * 2) you're not allowed to write any more of a unit test than is sufficient to fail; and compilation failures are failure  
  * 3) you're not allowed to write any more prod code than is sufficient to pass the one failing unit test
  * these laws will put you into a cycle! you  won't be able to get much of anything done before one of the other laws triggers an interrupt! but you'll get used to it.
  * how much debugging would you do if everything in the code worked a minute ago? not that much! in fact, the easiest way to debug if u do TDD is to just hit Ctrl+Z and get back to the last working test state!
  * you unit tests become code examples that document the system.
  * they should show everythign: how to create the right objects, what the valid state should be, etc. the code is what programmers want to read. its
  * writing code after the fact isn't very fun since you know the code works! Less satisfying. inevitably youo'll find code that's hard to test because it wasn't designed to be tested. it might seem a mountain too high. it discourages writing tests. and if u skip tests, then u know others are as well. this means that u now KNOW you cant trust the code. if it passes you can make no deicision based on that. if it fails, of course, youve got something you can work with. thats valuable. but if u cant trust the suite then u can make no decision on it passing.
  - if u write code first, then u must by design write code that is testable. its easy to test because it's decoupled.   
  - the goal of TDD is to write code that can be shipped!
  - TDD helps us _incrementally_ develop algorithms
  - Humble Object Pattern - a way to separate that which is difficult to test from that which is easy to test
- JUnit basics:
  * `@Test` / `@Before` / `@After` / `@BeforeClass` / `@AfterClass` / `@Rule`   
- Mocking
  * _test double_. it looks like a real thing, but it has different behavior
  * there are two types of test doubles: _mocks_ and _stubs_. a _mock_ is an auto-generated class. a framework like Mockito can provide this for me. a mock keeps counters that record which methods were called on it.
  - _stubs_. its something you write yourself. a hand-written class. it'll return pre-programmed behavior when certain methods are invoked.
  - InOrder io = Mockito.inOrder(Mockito.).verify (mock).a() ; InOrder.
- Spring Boot Testing Basics
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
