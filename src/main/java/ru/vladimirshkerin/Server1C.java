package ru.vladimirshkerin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The class contains the main logic of the server.
 *
 * @author Vladimir Shkerin
 * @since 24.06.2016
 */
public class Server1C {

    private List<Client1C> client1CList;          // list all objects Client1C
    private List<Thread> processList;   // list all process
    private boolean executed;           // flag executed server Server1C

    public Server1C() {
        this.client1CList = new ArrayList<>();
        this.processList = new ArrayList<>();
        this.executed = false;
    }

    public static void main(String[] args) {
        Server1C server1C = new Server1C();
        server1C.setExecuted(true);

        for (int i = 0; i < 5; i++) {
            String name = "bee_" + (i + 1);
            Command command = new Command("C:\\temp", new String[]{"ENTERPRISE", "/F"});
            Schedule schedule = new Schedule(6, -1, -1, -1, 30);
            Client1C client1C = new Client1C(name, command, schedule);
            server1C.addToBeeList(client1C);
        }

        do {
            Calendar calendar = new GregorianCalendar();
            for (Client1C client1C : server1C.getClient1CList()) {
                if (server1C.checkRunningProcess(client1C, calendar)) {
                    server1C.runProcess(client1C);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } while (server1C.isExecuted());

        server1C.stopAllProcess();
        server1C.setExecuted(false);
    }

    /**
     * Checked date in all Client1C for run process.
     *
     * @param client1C
     * @return
     */
    boolean checkRunningProcess(final Client1C client1C, final Calendar calendar) {
        Schedule schedule = client1C.getSchedule();
        return schedule.isExecute(calendar);
    }

    /**
     * Run process.
     *
     * @param client1C
     */
    void runProcess(final Client1C client1C) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client1C.getCommand();
            }
        });
        thread.setName(client1C.getName());
        thread.run();

        processList.add(thread);

        System.out.println("Thread \"" + thread.getName() + "\" run.");
    }

    /**
     * Stop all alive process.
     */
    void stopAllProcess() {
        for (Thread thread : getProcessList()) {
            if (thread.isAlive()) {
                thread.interrupt();
                System.out.println("Thread \"" + thread.getName() + "\" interrupt.");
            }
        }
    }

    public List<Client1C> getClient1CList() {
        return client1CList;
    }

    public boolean addToBeeList(Client1C client1C) {
        return client1CList.add(client1C);
    }

    public List<Thread> getProcessList() {
        return processList;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}

















