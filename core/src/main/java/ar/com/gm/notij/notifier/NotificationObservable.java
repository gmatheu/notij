package ar.com.gm.notij.notifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * Represents an object that need to be observed on notifications. Observers of
 * this class must {@link NotificationObserver} implementors and properly
 * registered. It is the subject of Observer pattern. Unlike {@link Observable},
 * it guarantees the same order in notifications as they were added.
 * 
 * @author gmatheu
 * 
 */
public class NotificationObservable {

	/**
	 * List of observers that will be notified on a notification event.
	 */
	private List<NotificationObserver> observers;
	/**
	 * Whether or not this subject has changed. I.e: needs to be notified or
	 * already notified to all observers.
	 */
	private boolean changed = false;

	/**
	 * Constructs an empty subject
	 */
	protected NotificationObservable() {
		super();
		observers = new ArrayList<NotificationObserver>();
	}

	/**
	 * If this object has changed, as indicated by the <code>hasChanged</code>
	 * method, then notify all of its observers and then call the
	 * <code>clearChanged</code> method to indicate that this object has no
	 * longer changed.
	 * <p>
	 * Each observer has its <code>update</code> method called with two
	 * arguments: this observable object and the <code>arg</code> argument.
	 * 
	 * @param notification
	 *            notification.
	 */
	public void notify(Notification notification) {
		setChanged();
		for (NotificationObserver observer : observers) {
			observer.update(this, notification);
		}

		clearChanged();
	}

	/**
	 * Adds an exception to the set of booking observers for this notification
	 * observable subject, provided that it is not the same as some observer
	 * already in the set.
	 * 
	 * @param o
	 *            an notification observer to be added.
	 * @throws NullPointerException
	 *             if the parameter o is null.
	 */
	public synchronized void addObserver(NotificationObserver o) {
		observers.add(0, o);
	}

	/**
	 * Deletes a notification observer from the set of observers of this
	 * notification observable subject. Passing <code>null</code> to this method
	 * will have no effect.
	 * 
	 * @param o
	 *            the booking observer to be deleted.
	 */
	public synchronized void deleteObserver(NotificationObserver o) {
		observers.remove(o);
	}

	/**
	 * Deletes the first notification observer in list
	 */
	public synchronized void deleteFirstObserver() {
		if (!observers.isEmpty()) {
			observers.remove(0);
		}
	}

	/**
	 * Marks this <tt>NotificationObservable</tt> object as having been changed;
	 * the <tt>hasChanged</tt> method will now return <tt>true</tt>.
	 */
	protected synchronized void setChanged() {
		changed = true;
	}

	/**
	 * Indicates that this object has no longer changed.
	 */
	protected synchronized void clearChanged() {
		changed = false;
	}

	/**
	 * Tests if this object has changed.
	 * 
	 * @return <code>true</code> if and only if the <code>setChanged</code>
	 *         method has been called more recently than the
	 *         <code>clearChanged</code> method on this object;
	 *         <code>false</code> otherwise.
	 */
	public synchronized boolean hasChanged() {
		return changed;
	}

	/**
	 * Wraps information of a notification
	 * 
	 * @author gmatheu
	 * 
	 */
	public static class Notification {
		/**
		 * Enumerates levels of notifications
		 * 
		 * @author gmatheu
		 * 
		 */
		public enum Level {
			INFO, WARN, ERROR;
		}

		/**
		 * Main message to be notified
		 */
		private String message;
		/**
		 * Additional information that may be used
		 */
		private Throwable exception;
		/**
		 * Level of notification
		 */
		private Level level;
		/**
		 * Determines whether or not this notification has been consumed (e.g: A
		 * {@link NotificationObserver} processed and there is no need to keep notifying.)
		 */
		private boolean isConsumed;

		/**
		 * 
		 * By default, {@link #isConsumed} is set to false
		 * 
		 * @param message
		 *            the message
		 * @param exception
		 *            the exception
		 * @param level
		 *            notification level
		 */
		public Notification(String message, Throwable exception, Level level) {
			super();
			this.message = message;
			this.exception = exception;
			this.level = level;
			this.isConsumed = false;
		}

		/**
		 * Assumes null exception.
		 * 
		 * @param message
		 * @param level
		 */
		public Notification(String message, Level level) {
			this(message, null, level);

		}

		/**
		 * @return {@link #message}
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * 
		 * @return exception
		 */
		public Throwable getException() {
			return exception;
		}

		/**
		 * 
		 * @return level
		 */
		public Level getLevel() {
			return level;
		}

		/**
		 * 
		 * @return isConsumed
		 */
		public boolean isConsumed() {
			return isConsumed;
		}

		/**
		 * Sets isConsumed
		 */
		public void setConsumed() {
			this.isConsumed = true;
		}

		/**
		 * Format: "{@link #getLevel()}: {@link #getMessage()}"
		 */
		@Override
		public String toString() {
			return String.format("%s: %s", getLevel(), getMessage());
		}
	}
}
