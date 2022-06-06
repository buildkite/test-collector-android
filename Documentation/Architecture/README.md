# Architecture Decision Records

We use lightweight Architecture Decision Records to record our decision making in this project. If you are unfamiliar with ADRs please read [ADR-0001][1] which provides some background.

There is also a [template][0] in this directory for use when you create an ADR.

## When Should I Write an Architecture Decision Record?

> This section was "inspired" by a [Spotify Engineering blog](https://engineering.atspotify.com/2020/04/14/when-should-i-write-an-architecture-decision-record/) on the same subject.

An Architecture Decision Record (ADR) is a document that captures a decision, including the context of how the decision was made and the consequences of adopting the decision.

The iOS team utilizes ADRs to document decisions made related to mobile application system design and engineering best practices. We typically arrive at these decisions through discussion in a Request for Comments (RFCs) or during our engineering meetings.

### What are the benefits?

- **Onboarding** - Future team members are able to read a history of decisions and quickly get up to speed on how and why a decision is made, and the impact of that decision.
- **Ownership Handover** - When our team changes, we sometimes have to move ownership of systems from one team member to another. A lot of context/knowledge can be lost when this takes place, decreasing productivity. This problem is less severe with ADRs. New owners of a system can quickly get up to speed with how and why the system’s architecture evolved in the way it did simply by reading through the ADRs.
- **Alignment** - ADRs make it easier for our team to align on best practices. Alignment has the benefit of removing duplicative efforts, and making code more reusable and consistent.

### When should I write one?

An ADR should be written whenever a decision of significant impact is made. To get you started, below are a few scenarios for determining when to write an ADR:

### Backfilling decisions

Sometimes a decision was made, or an implicit standard forms naturally on its own, but because it was never documented, it’s not clear to everyone (especially new hires) that this decision exists. If a tree falls in a forest and no one is around to hear it, does it make a sound? Similarly, if a decision was made but it was never recorded, can it be a standard? One way to identify an undocumented decision is during Peer Review. The introduction of a competing code pattern or library could lead the reviewer to discover an undocumented decision. Below is my mental model for when to backfill an architecture decision:

- Do I have a problem? *Yes*
- Is there a blessed solution? *Yes*
- Is it documented? *No*
- **Write an ADR!**

### Proposing large changes

Over the lifecycle of a system, you will have to make decisions that have a large impact on how it is designed, maintained, extended, and more. As requirements evolve, you may need to introduce a breaking change to your API, which would require a migration from your consumers. We have system design reviews, architecture reviews, and RFCs to facilitate agreements on an approach or implementation. When these processes run their course, how should we capture the decisions made? Below is my mental model for how to document these large changes:

- Do I have a problem? *Yes*
- Is there a blessed solution? *No*
- Do I have a solution? *Yes*
- Is it a big change? *Yes*
- **Write an RFC!**
- Did my RFC conclude with a solution? *Yes*
- **Write an ADR!**

### Proposing small/no changes

In our day-to-day, we make small decisions that have little to no impact. The cost of undocumented decisions is hard to measure, but the effects usually include duplicated efforts (other engineers try to solve the same problems) or competing solutions (two third-party libraries that do the same thing). Enough small decisions can compound into a future problem that requires a large process or effort (ie. migration). Documenting these decisions doesn’t have to cost much. ADRs can be lightweight. Below is my mental model for working through this:

- Do I have a problem? *Yes*
- Is there a blessed solution? *No*
- Do I have a solution? *Yes*
- Is it a big change? *No*
- **Write an ADR!**

## Superseding Decisions

When an architectural decision is superseded by a subsequent decision please ensure the status of the superseded ADR in the table below and in the ADR document itself is updated to reflect that fact.

## Record Log

| ID  | Status | Title |
|-----|--------|-------|
| [ADR-0001][1] | **Accepted** | Architecture Decision Records |

[0]: ADR-0000-template.md
[1]: ADR-0001-architecture-decision-records.md
