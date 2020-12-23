package uz.pop.astroidradar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
Below is what I recommend you to do:

- Run your starter code and be sure that it runs.

- You have obtained your API key which is good.

- Create your viewmodel,

- Try to hit the API endpoints and log the data to the terminal to be sure that you can successfully access the API.

- Configure your database, retrofit and repository and use with your API.

- Create view to display the asteriods. Don't bother about the beauty of the UI for now. You just need to have some skeleton and make sure items can be displayed.

- Create a view for each item.

- Use recyclerview in your viewmodel to populate the items to screen.

- Work on your displaying the image of the day.

- Create your asteroid details UI. Remember just come basic UI to be able to see the details.

- Connect your list screenshot to listen to clicks and then navigate to the details fragment.

- You can now work on arranging you UI to look good.

- Test your app together and see how it goes.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}