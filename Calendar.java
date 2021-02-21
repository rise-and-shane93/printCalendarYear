import java.util.Scanner; // import Scanner

public class Calendar {
    public static void main(String[] args) {

        // Initialize Scanner and prompt the user for the year as a number
        Scanner input = new Scanner(System.in);
        System.out.print("Welcome to the calendar program. Please enter the desired year (ex:2021): ");
        int userYear = input.nextInt();

        // Outputs each month's calendar for the year that the user inputted
        printYearCalendar(userYear);
    }


    /****
     The method getStartDay() implements Zeller's Algorithm for determining the day of the
    week the first day of a month is. The method adjusts Zeller's numbering scheme
    for day of week ( 0=Saturday, ..., 6=Friday ) to conform to a day of week number
    that corresponds to ISO conventions (i.e., 1=Monday, ..., 7=Sunday)
    
    Pre-Conditions: The month value, m,  is 1-12
                            The day value, d, is 1-31, or 1-28, 1-29 for February
                            The year value, y, is the full year (e.g., 2012)
                    
    Post-Conditions: A value of 1-7 is returned, representing the first day of the month
                            1 = Monday, ..., 7 = Sunday
    ****/

    public static int getStartDay( int m, int d, int y ) {
        // Adjust month number & year to fit Zeller's numbering system
        if (m < 3) 
        {
            m = m + 12;
            y = y - 1;
        }
        
        int k = y % 100;      // Calculate year within century
        int j = y / 100;      // Calculate century term
        int h = 0;            // Day number of first day in month 'm'
        
        h = ( d + ( 13 * ( m + 1 ) / 5 ) + k + ( k / 4 ) + ( j / 4 ) +
            ( 5 * j ) ) % 7;
        
        // Convert Zeller's value to ISO value (1 = Mon, ... , 7 = Sun )
        int dayNum = ( ( h + 5 ) % 7 ) + 1;     
        
        return dayNum;
    }

    // Method to output each month's calendar for the year the user inputted
    public static void printYearCalendar(int year) {

        // for loop to output each month's calendar for the year the user inputted
        for (int i = 1; i <= 12; i++) {
            printMonthCalendar(i,year);
        }
    }

    // Method for outputting the calendar
    public static void printMonthCalendar(int m, int y) {

        // Both print statements add space on the top and bottom of the calendar
        System.out.println();

        // Method for printing the calendar's header
        printMonthHeader(m,y);

        // Method for printing the calendar's body
        printMonthBody(m,y);

        System.out.println("\n");
    }

    // Method for printing the calendar's header
    public static void printMonthHeader( int m, int y ) {

        // get the month's name from the getMonthName method
        String selectedMonthName = getMonthName(m);

        // Print a tab character, the month, a space and the year
        System.out.println("\t" + selectedMonthName + " " + y);

        // for loop to output 29 hyphens to separate the month/year and the day names
        for (int x = 0; x < 29; x++) {
            System.out.print("-");
        }

        // Print the three letter abbreviations of each day of the week
        System.out.println("\n Sun Mon Tue Wed Thu Fri Sat ");
    }
    
    // Method for printing the calendar's body
    public static void printMonthBody(int m, int y) {

        // get the number of days in the month and which day the month starts
        int daysInMonth = getNumDaysInMonth(m,y);
        int startDay = getStartDay(m,1,y);

        /* get the number of spaces to print before printing the first day 
        by using the getSpacesForStartDay method */
        String spacesBeforeFirstDay = getSpacesForStartDay(startDay);
        
        int nextDayIntStart = startDay; // initialized to the number corresponding to the first day of the month

        /* initizlied to 0 and has a range from 1 to 7. 
        This tracks which day of the week the next day is. */
        int nextDayInt = 0; 

        String spaceBetweenDigits = ""; // used to add the correct number of spaces between each number

        // for loop to output each day of the month to be vertically aligned underneath each day of the week
        for (int x = 1; x <= daysInMonth; x++) {

                /* If the day of the month is less than 9, put three spaces between each number.
                Otherwise, if the day is greater than or equal to 9, put two spaces between each number */
                if (x < 9) {
                    spaceBetweenDigits = "   ";
                } else if (x >= 9) {
                    spaceBetweenDigits = "  ";
                }

                /* Only for the first day of the month, print the required number of spaces to be 
                aligned underneath the correct day */
                if (x == 1) {
                    System.out.print(spacesBeforeFirstDay);
                }

                // reassign the return value of the getNextDayInt to the nextDayInt variable
                nextDayInt = getNextDayInt(nextDayIntStart);

                /* If the nextDayInt is 7, add a newline to start the next week
                and the correct number of spaces between the numbers.
                Otherwise, print the day and the number of spaces. */
                if (nextDayInt == 7) {
                    System.out.print(x + "\n" + spaceBetweenDigits);
                } else {
                    System.out.print(x + spaceBetweenDigits);
                }

                /* Increment the nextDayIntStart variable to later 
                reassign the value of the nextDayInt variable. */
                nextDayIntStart++;
        }
    }

    /* Method to get the integer of the next day.
    The range is between 1 and 7. */
    public static int getNextDayInt(int d) {
        int nextDay = 0;
        
        // reassigned the value of the nextDayIntStart variable in the printMonthBody method
        nextDay = d;

        /* If the nextDay variable is less than 7, increment the variable.
        Otherwise, if the nextDay variable is 7, reassign it to 1.
        Finally, if it is greater than 7, get the remainder of nextDay divided by 7 and add 1.
        This will reset it to be the correct number between 1 and 7 */
        if (nextDay < 7) {
            nextDay++;
        } else if (nextDay == 7) {
            nextDay = 1;
        } else if (nextDay > 7) {
            nextDay = (nextDay % 7) + 1;
        }
        return nextDay;

    }

    // Method to get the correct number of space for the first day of the month
    public static String getSpacesForStartDay(int d) {
        int numSpaces = 0;
        String spaces = "";
        switch(d) {
            case 1:
                numSpaces = 7;
                break;
            case 2:
                numSpaces = 11;
                break;
            case 3:
                numSpaces = 15;
                break;
            case 4:
                numSpaces = 19;
                break;
            case 5:
                numSpaces = 23;
                break;
            case 6:
                numSpaces = 27;
                break;
            case 7:
                numSpaces = 3;
                break;
        }

        // for loop to assign the correct number of spaces to the numSpaces variable
        for (int x = 0; x < numSpaces; x++) {
            spaces += " ";
        }

        return spaces;
    }

    // Method to get the month name based off the number that the user inputted
    public static String getMonthName( int m ) {
        String monthName = "January";
        switch (m) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "Improper input detected. Please try again";
                break;
                
        }
        return monthName;
    }

    // Method to get the number of days in a month
    public static int getNumDaysInMonth( int m, int y) {
        
        /* checks if the year that the user inputted is a leap year
        and initializes this variable to either 29 or 28 */
        int daysinFeb = isLeapYear(y) ? 29 : 28;

        int days = 0;
        switch (m) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = daysinFeb;
                break;
        }
        return days;
    }

    // Method to check if the year the user inputted is a leap year
    public static boolean isLeapYear( int y ) {
        int year = y;
        boolean divByFour = year % 4 == 0 ? true : false;
        boolean isCentury = year % 100 == 0 ? true : false;
        boolean fourHundredYearsCentury = year % 400 == 0 ? true : false;
        boolean leapYearBool = false;

        /* If the year the user inputted is divisible by 4, not a century, 
        and if it's a century divisible by 400, it's a leap year */
        if (divByFour) {
            if (isCentury) {
                if (fourHundredYearsCentury) {
                    leapYearBool = true;
                } else {
                    leapYearBool = false;
                }
            } else {
                leapYearBool = true;
            }
        } else {
            leapYearBool = false;
        }
        return leapYearBool;
    }

}