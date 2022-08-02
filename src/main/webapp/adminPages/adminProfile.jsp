<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" />

    <link rel="stylesheet" type="text/css" href="https://rawgit.com/nobleclem/jQuery-MultiSelect/master/jquery.multiselect.css" />
    <link rel="stylesheet" href="adminStyle.css">
    <script type="text/javascript" src="adminScript.js"></script>

</head>
<body>

<div class="header">
    <div class="header-right">
        <a class="active" href="#home">Home</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
    </div>
</div>

<div class="sidenav">
    <a href="#about">link</a>
    <a href="#services">link</a>
    <a href="#clients">link</a>
    <a href="#contact">link</a>
</div>

<div class="main">

    <form class="form-inline" action="/action_page.php">
        <fieldset style="max-width: 100%;">
            <h2>Employee Database Refine</h2>

            <label for="position">Position:</label>
            <select id="position" name="position">
                <option value=""></option>
                <option value="javascriptDev">Javascript Developer</option>
                <option value="supportLead">Support Lead</option>
                <option value="softwareEng">Software Engineer</option>
            </select>

            <label for="location">Location:</label>
            <select id="location" name="location">
                <option value=""></option>
                <option value="australia">Australia</option>
                <option value="canada">Canada</option>
                <option value="usa">USA</option>
            </select>


            <label for="level">Level:</label>
            <select id="level" name="level">
                <option value=""></option>
                <option value="australia">Beginner (0-2yrs)</option>
                <option value="canada">Intermediate (2-5yrs)</option>
                <option value="usa">Expert (5+yrs)</option>
            </select>

            <div class="multiselect">
                <label for="location">Skill:</label>
                <div class="selectBox" onclick="showCheckboxes()">
                    <select>
                        <option>Select a skill</option>
                    </select>
                    <div class="overSelect"></div>
                </div>
                <div id="checkboxes">
                    <label for="one">
                        <input type="checkbox" id="one" />Javascript</label>
                    <label for="two">
                        <input type="checkbox" id="two" />SQL</label>
                    <label for="three">
                        <input type="checkbox" id="three" />C#</label>
                </div>
            </div>

            <button type="submit">Search</button>

        </fieldset>
    </form>
    <div class="fieldset2">
        <fieldset>
            <table id="example" class="display">
                <thead>
                <tr>
                    <th></th>
                    <th>Name</th>
                    <th>Position</th>
                    <th>Location</th>
                    <th>Skill(s)</th>
                    <th>Level</th>
                    <th>Manager</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td></td>
                    <td>Cort B</td>
                    <td>Front-End Designer</td>
                    <td>Augusta, GA</td>
                    <td>HTML, CSS, Javascript</td>
                    <td>Beginner</td>
                    <td>Prescott</td>
                </tr>
                <tr>
                    <td></td>
                    <td>Chris Brown</td>
                    <td>Software Developer</td>
                    <td>Augusta, GA</td>
                    <td>C++, SQL</td>
                    <td>Intermediate</td>
                    <td>Sepielle</td>
                </tr>

                <tr>
                    <td></td>
                    <td>Colleen Hurst</td>
                    <td>Javascript Developer</td>
                    <td>San Francisco</td>
                    <td>Javascript, Angular</td>
                    <td>Expert</td>
                    <td>Prescott</td>
                </tr>
                <tr>
                    <td></td>
                    <td>Sonya Frost</td>
                    <td>Software Engineer</td>
                    <td>Edinburgh</td>
                    <td>C#</td>
                    <td>Intermediate</td>
                    <td>Prescott</td>
                </tr>
                <tr>
                    <td></td>
                    <td>Jena Gaines</td>
                    <td>Office Manager</td>
                    <td>Grovetown</td>
                    <td>Dev Ops</td>
                    <td>Expert</td>
                    <td>Sepielle</td>
                </tr>
                <tr>
                    <td></td>
                    <td>Quinn Flynn</td>
                    <td>Support Lead</td>
                    <td>Augusta, GA</td>
                    <td>C++, C#, Java</td>
                    <td>Expert</td>
                    <td>Prescott</td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <th></th>
                    <th>Name</th>
                    <th>Position</th>
                    <th>Location</th>
                    <th>Skill(s)</th>
                    <th>Level</th>
                    <th>Manager</th>
                </tr>
                </tfoot>
            </table>
        </fieldset>
    </div>

</div>


</body>
</html>
