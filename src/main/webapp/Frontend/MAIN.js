document.getElementById('Crear').addEventListener('click',
    function(){
        document.querySelector('.PopUp').style.display = 'flex';
    });
    
    document.querySelector('.close').addEventListener('click',
    function(){
        document.querySelector('.PopUp').style.display = 'none';
    });

    document.getElementById('CrearReward').addEventListener('click',
        function(){
            document.querySelector('.PopUpReward').style.display = 'flex';
        });
        
        document.querySelector('.close-reward').addEventListener('click',
        function(){
            document.querySelector('.PopUpReward').style.display = 'none';
        });