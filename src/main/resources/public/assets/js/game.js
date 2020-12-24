// TODO: Browser compatability.

const game = {

    ready: false,

    players: new Map(),

    onload: function () {
        if (!me.video.init(640, 480, {parent : "screen", scale : "auto", scaleMethod : "flex-width"})) {
            alert("Your browser does not support HTML5 canvas.");
            return;
        }
        me.loader.preload(game.resources, game.loaded);      
    },

    loaded: function () {

        game.animations = {
            santa: new me.video.renderer.Texture(
                me.loader.getJSON("santa_animation_data"),
                me.loader.getImage("santa_animation")
            )
        };

        me.state.set(me.state.PLAY, new game.PlayScreen());

        me.pool.register("playerEntity", game.PlayerEntity, true);

        me.input.bindKey(me.input.KEY.LEFT,  "left");
        me.input.bindKey(me.input.KEY.RIGHT, "right");
        me.input.bindKey(me.input.KEY.UP, "up");
        me.input.bindKey(me.input.KEY.DOWN, "down");

        me.state.change(me.state.PLAY);
        
    }
    
};

game.socket = new WebSocket(`ws://${window.location.hostname}:${window.location.port || '80'}/game`);

game.socket.onopen = () => {

    if (game.ready) {
        game.socket.send(JSON.stringify({
            id: 0,
            packet: {
                name: "TODO",
                colorType: -1
            }
        }));
    }
};

game.socket.onmessage = message => {
    const wrapper = JSON.parse(message.data);
    switch (wrapper.id) {
        case 1:
            game.players.get(wrapper.packet.playerId).x = wrapper.packet.x;
            game.players.get(wrapper.packet.playerId).y = wrapper.packet.y;
        break;
        case 4:
            const newPlayer = me.pool.pull('playerEntity', wrapper.packet.x, wrapper.packet.y, {
                width: 128,
                height: 128,
                playerId: wrapper.packet.playerId
            });
            game.players.set(wrapper.packet.playerId, { x: wrapper.packet.x, y: wrapper.packet.y, entity: newPlayer });
            me.game.world.addChild(newPlayer);
        break;
        case 5:
            me.game.world.removeChild(game.players.get(wrapper.packet.playerId).entity);
            game.players.delete(wrapper.packet.playerId);
        break;
    }
};

game.PlayScreen = me.Stage.extend({

    onResetEvent: function () {

        if (game.socket.readyState === WebSocket.OPEN) {
            game.socket.send(JSON.stringify({
                    id: 0,
                    packet: {
                        name: "TODO",
                        colorType: -1
                    }
            }));
        }
        game.ready = true;
        
        me.levelDirector.loadLevel("main_map");

        const player = me.pool.pull('playerEntity', 700, 1500, {
            width: 128,
            height: 128,
            playerId: null
        });
        me.game.world.addChild(player);


    },

    onDestroyEvent: function () {

    }

});