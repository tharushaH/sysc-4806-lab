$(document).ready(function() {
    const addressBookId = 1;
    const buddyList = $('.buddy-list');

    function loadBuddies() {
        $.ajax({
            url: `http://localhost:8080/api/addressbooks/${addressBookId}`,
        }).then(function(data) {
            $('.addressbook-id').text("Address Book ID: " + data.id);
            buddyList.empty();

            if (data.buddies && data.buddies.length > 0) {
                data.buddies.forEach(function(buddy) {
                    const buddyItem = $(`
                        <div data-id="${buddy.id}">
                            <p><strong>Name:</strong> ${buddy.name}</p>
                            <p><strong>Phone:</strong> ${buddy.phoneNumber}</p>
                            <p><strong>Address:</strong> ${buddy.address}</p>
                            <button class="delete-buddy">Delete</button>
                            <hr>
                        </div>
                    `);
                    buddyList.append(buddyItem);
                });
            } else {
                buddyList.append("<p>No buddies found in this address book.</p>");
            }
        });
    }

    // Initial load
    loadBuddies();

    // Delete buddy
    buddyList.on('click', '.delete-buddy', function() {
        const buddyId = $(this).parent().data('id');
        $.ajax({
            url: `http://localhost:8080/api/addressbooks/${addressBookId}/buddies/${buddyId}`,
            type: 'DELETE'
        }).then(() => loadBuddies());
    });

    $('#add-buddy').click(function() {
        const newBuddy = {
            name: $('#buddy-name').val(),
            phoneNumber: $('#buddy-phone').val(),
            address: $('#buddy-address').val()
        };

        $.ajax({
            url: `http://localhost:8080/api/addressbooks/${addressBookId}/buddies`,
            type: 'POST',
            contentType: 'application/json', // IMPORTANT!
            data: JSON.stringify(newBuddy),   // Convert JS object to JSON
            success: function() {
                $('#buddy-name').val('');
                $('#buddy-phone').val('');
                $('#buddy-address').val('');
                loadBuddies();
            },
            error: function(xhr, status, error) {
                console.error("Error adding buddy:", status, error);
            }
        });
    });

});
