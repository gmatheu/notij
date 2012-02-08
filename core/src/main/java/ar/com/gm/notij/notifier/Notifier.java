package ar.com.gm.notij.notifier;

import ar.com.gm.notij.notifier.NotificationObservable.Notification.Level;

/**
 * {@link NotificationObservable} implementation used to spread notifications
 * messages across all observers.
 * 
 * 
 * @author gmatheu
 * 
 */
public class Notifier extends NotificationObservable {

	/**
	 * Singleton instance
	 */
	private static Notifier instance;

	/**
	 * Returns singleton instance of Notifier. It creates the instance if it is
	 * the first time that it is called.
	 * 
	 * @return the instance
	 */
	public static Notifier getInstance() {
		if (instance == null) {
			instance = new Notifier();
		}
		return instance;
	}

	/**
	 * Default constructor
	 */
	private Notifier() {
		super();
	}

	/**
	 * Fires a customized by parameters notification.
	 * 
	 * @param message
	 *            the message
	 * @param exc
	 *            exception
	 * @param level
	 *            level of notification
	 */
	private void notify(String message, Exception exc, NotificationObservable.Notification.Level level) {
		Notification notification = new Notification(message, exc, level);
		notify(notification);
	}

	/**
	 * Fires a notification with message as {@link Notification} message and
	 * {@link Level#INFO} level.
	 * 
	 * @param message
	 *            the message
	 */
	public void notify(String message) {
		notify(message, null, NotificationObservable.Notification.Level.INFO);
	}

	/**
	 * Convenience method to fire a {@link Level#INFO} notification with null
	 * exception on {@value #instance}.
	 * 
	 * @see #notify(String)
	 * @param message
	 */
	public static void notifyInformation(String message) {
		getInstance().notify(message);
	}

	/**
	 * Convenience method to fire a {@link Level#WARN} notification with null
	 * exception on {@value #instance}.
	 * 
	 * @see #notify(String, Exception, Level))
	 * @param message
	 */
	public static void notifyWarning(String message) {
		getInstance().notify(message, null, NotificationObservable.Notification.Level.WARN);
	}

	/**
	 * Convenience method to fire a {@link Level#ERROR} notification with null
	 * exception on {@value #instance}.
	 * 
	 * @see #notify(String, Exception, Level)
	 * @param message
	 */
	public static void notifyError(String message) {
		getInstance().notify(message, null, NotificationObservable.Notification.Level.ERROR);
	}

	/**
	 * Convenience method to fire a {@link Level#ERROR} notification with exc
	 * exception on {@value #instance}.
	 * 
	 * @see #notify(String, Exception, Level)
	 * @param message
	 * @param exc
	 *            exception
	 */
	public static void notifyException(String message, Exception exc) {
		getInstance().notify(message, exc, NotificationObservable.Notification.Level.ERROR);
	}

	/**
	 * Convenience method to fire a {@link Level#ERROR} notification with exc
	 * exception and {@link Exception#getMessage()} as message on
	 * {@value #instance}.
	 * 
	 * @see #notifyException(String, Exception)
	 * @param message
	 * @param exc
	 *            exception
	 */
	public static void notifyException(Exception exc) {
		notifyException(exc.getMessage(), exc);
	}

	/**
	 * Convenience method to fire a {@link Level#ERROR} notification with null
	 * exception on {@value #instance}.
	 * 
	 * @see #notifyException(String, Exception)
	 * @param message
	 */
	public static void notifyException(String message) {
		notifyException(message, null);
	}
}
