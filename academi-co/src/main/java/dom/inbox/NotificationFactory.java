package dom.inbox;

/**
 * Instantiator for Notification
 * 
 * @author kaikoveritch
 *
 */
public class NotificationFactory {

	static public Notification createNotification(String body) {
		return new ConcreteNotification(body);
	}
}
