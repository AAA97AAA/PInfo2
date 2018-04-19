package dom.inbox;

public class InboxFactory {

	static public Inbox createInbox() {
		return new ConcreteInbox();
	}
}
